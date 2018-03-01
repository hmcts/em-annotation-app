package uk.gov.hmcts.reform.em.annotation.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.reform.auth.checker.spring.serviceonly.AuthCheckerServiceOnlyFilter;
import uk.gov.hmcts.reform.em.annotation.componenttests.Helper;
import uk.gov.hmcts.reform.em.annotation.componenttests.backdoors.ServiceResolverBackdoor;
import uk.gov.hmcts.reform.em.annotation.componenttests.backdoors.UserResolverBackdoor;
import uk.gov.hmcts.reform.em.annotation.componenttests.sugar.RestActions;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.Comment;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static uk.gov.hmcts.reform.em.annotation.componenttests.Helper.ANNOTATION_SET_ENDPOINT;
import static uk.gov.hmcts.reform.em.annotation.componenttests.Helper.GOOD_ANNOTATION_SET_MINIMUM_STR;

@RunWith(SpringRunner.class)
@ActiveProfiles({"local"})
@SpringBootTest(webEnvironment = MOCK)
@Transactional
@EnableSpringDataWebSupport
@DirtiesContext
public class AnnotationControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    protected ServiceResolverBackdoor serviceRequestAuthorizer;

    @Autowired
    protected UserResolverBackdoor userRequestAuthorizer;

    @Autowired
    protected AuthCheckerServiceOnlyFilter filter;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ConfigurableListableBeanFactory configurableListableBeanFactory;

    protected RestActions restActions;

    protected MockMvc mvc;

    private final HttpHeaders headers = Helper.getHeaders();

    @Before
    public void setUp() {
        mvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        this.restActions = new RestActions(mvc, serviceRequestAuthorizer, userRequestAuthorizer, objectMapper);
        filter.setCheckForPrincipalChanges(true);
        filter.setInvalidateSessionOnPrincipalChange(true);
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void should_save_a_single_annotation() throws Exception {
        String annotationUrl = uploadAnnotationSetAndGetPostAnnotationUrl();

        mvc.perform(post(annotationUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder().height(100L).build())))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$._links.self.href", notNullValue()));
    }

    @Test
    public void should_get_a_single_annotation() throws Exception {
        String annotationUrl = uploadAnnotationSetAndGetPostAnnotationUrl();

        MockHttpServletResponse response = mvc.perform(post(annotationUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder().height(100L).build())))
            .andExpect(jsonPath("$._links.self.href", notNullValue()))
            .andReturn().getResponse();

        String annotationSelfUrl = getAnnotationSelfUrlFromResponse(response);

        mvc.perform(get(annotationSelfUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._links.self.href", containsString(annotationUrl)));
    }

    @Test
    public void should_update_a_single_annotation() throws Exception {
        String annotationPostUrl = uploadAnnotationSetAndGetPostAnnotationUrl();

        MockHttpServletResponse response = mvc.perform(post(annotationPostUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder().height(100L).build())))
            .andExpect(jsonPath("$._links.self.href", notNullValue()))
            .andReturn().getResponse();

        String annotationSelfUrl = getAnnotationSelfUrlFromResponse(response);

        mvc.perform(put(annotationSelfUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder().height(200L).build())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.height", equalTo(200)))
            .andExpect(jsonPath("$._links.self.href", containsString(annotationPostUrl)));
    }

    @Test
    public void should_update_a_single_annotation_comment() throws Exception {
        String annotationPostUrl = uploadAnnotationSetAndGetPostAnnotationUrl();

        MockHttpServletResponse response = mvc.perform(post(annotationPostUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder()
                .comments(ImmutableSet.of(Comment.builder().content("Comment").build()))
                .build())))
            .andExpect(jsonPath("$._links.self.href", notNullValue()))
            .andReturn().getResponse();

        String annotationSelfUrl = getAnnotationSelfUrlFromResponse(response);

        mvc.perform(put(annotationSelfUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder()
                .comments(ImmutableSet.of(Comment.builder().content("New Comment").build()))
                .build())))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.comments[0].content", equalTo("New Comment")))
            .andExpect(jsonPath("$._links.self.href", containsString(annotationPostUrl)));
    }

    @Test
    public void should_delete_a_single_annotation() throws Exception {
        String annotationPostUrl = uploadAnnotationSetAndGetPostAnnotationUrl();

        MockHttpServletResponse response = mvc.perform(post(annotationPostUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder().height(100L).build())))
            .andExpect(jsonPath("$._links.self.href", notNullValue()))
            .andReturn().getResponse();

        String annotationSelfUrl = getAnnotationSelfUrlFromResponse(response);

        mvc.perform(delete(annotationSelfUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(Annotation.builder().height(200L).build())))
            .andExpect(status().isNoContent());

        mvc.perform(get(annotationSelfUrl)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }



    private String uploadAnnotationSetAndGetPostAnnotationUrl() throws Exception {
        MockHttpServletResponse response = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        return getAddAnnotationUrlFromResponse(response);
    }

    public static String getAddAnnotationUrlFromResponse(MockHttpServletResponse response) throws IOException {
        final String path = "/_links/add-annotation/href";
        return getPathFromResponse(response, path);
    }

    public static String getAnnotationSelfUrlFromResponse(MockHttpServletResponse response) throws IOException {
        final String path = "/_links/self/href";
        return getPathFromResponse(response, path);
    }

    private static String getPathFromResponse(MockHttpServletResponse response, String path) throws IOException {
        final String content = response.getContentAsString();
        return getNodeAtPath(path, content)
            .asText()
            .replace("http://localhost", "");
    }

    private static JsonNode getNodeAtPath(String path, String content) throws IOException {
        return MAPPER
            .readTree(content)
            .at(path);
    }
}
