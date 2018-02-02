package uk.gov.hmcts.reform.em.annotation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Assert;
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
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;
import uk.gov.hmcts.reform.auth.checker.spring.serviceonly.AuthCheckerServiceOnlyFilter;
import uk.gov.hmcts.reform.em.annotation.componenttests.Helper;
import uk.gov.hmcts.reform.em.annotation.componenttests.backdoors.ServiceResolverBackdoor;
import uk.gov.hmcts.reform.em.annotation.componenttests.backdoors.UserResolverBackdoor;
import uk.gov.hmcts.reform.em.annotation.componenttests.sugar.CustomResultMatcher;
import uk.gov.hmcts.reform.em.annotation.componenttests.sugar.RestActions;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static uk.gov.hmcts.reform.em.annotation.componenttests.Helper.getUuid;


@RunWith(SpringRunner.class)
@ActiveProfiles({"embedded", "local", "componenttest"})
@SpringBootTest(webEnvironment = MOCK)
@Transactional
@EnableSpringDataWebSupport
@DirtiesContext
public class StoredAnnotationSetControllerTest {

    @Autowired
    protected ServiceResolverBackdoor serviceRequestAuthorizer;

    @Autowired
    protected UserResolverBackdoor userRequestAuthorizer;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Autowired
    protected AuthCheckerServiceOnlyFilter filter;

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

    CustomResultMatcher body() {
        return new CustomResultMatcher(objectMapper);
    }

    @SneakyThrows
    String contentsOf(String fileName) {
        String content = new String(Files.readAllBytes(Paths.get(ResourceUtils.getURL("classpath:" + fileName).toURI())), StandardCharsets.UTF_8);
        return resolvePlaceholders(content);
    }

    String resolvePlaceholders(String content) {
        return configurableListableBeanFactory.resolveEmbeddedValue(content);
    }

    public void should_upload_empty_annotation_set_and_retive_annotation_set() throws Exception {
        AnnotationSet annotationSet = AnnotationSet.builder()
            .annotations(
                ImmutableSet.of(
                    Annotation.builder()
                        .page((long) 10)
                        .build())
            ).build();

        final MockHttpServletResponse response = mvc.perform(post("/annotationSets",annotationSet)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        final String url = getUuid(response);

        final MockHttpServletResponse getResp = mvc.perform(get(url)
            .headers(headers))
            .andReturn().getResponse();


        System.out.println(getResp.getContentAsString());

    }

    @Autowired
    private StoredAnnotationSetController storedAnnotationSetController;

    @Test
    public void postAnnotationSet() throws Exception {
        AnnotationSet annotationSet = AnnotationSet.builder()
            .annotations(
                ImmutableSet.of(
                    Annotation.builder()
                        .page((long) 10)
                        .build())
            ).build();

        mvc.perform(post("/annotationSets")
            .headers(headers));

        mvc.perform(post("/annotationSets",annotationSet)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }

    @Test
    public void postAnnotationSetMin() throws Exception {
        String annotationSet = "{\"annotations\":[{\"page\":10}]}";

        mvc.perform(post("/annotationSets")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(annotationSet))
            .andExpect(status().isOk());
    }

    @Test
    public void postAnnotationSetMax() throws Exception {
        AnnotationSet annotationSet = AnnotationSet.builder()
            .createdBy("Alec")
            .annotations(
                ImmutableSet.of(
                    Annotation.builder()
                        .page((long) 10)
                        .build(),
                    Annotation.builder()
                        .page((long) 10)
                        .build()
                )
            ).build();

        mvc.perform(post("/annotationSets",annotationSet)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    }

    @Test
    public void postAnnotationEmpty() throws Exception {
        String annotationSet = "";

        mvc.perform(post("/annotationSets")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(annotationSet))
            .andExpect(status().isBadRequest());

    }

    @Test
    public void postAnnotationMalformed() throws Exception {
        String annotationSet = "{ \\daksfakbl'':asbahfj}";

        mvc.perform(post("/annotationSets")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(annotationSet))
            .andExpect(status().isBadRequest());

    }

    @Test
    public void postAnnotatioWrongObject() throws Exception {
        Annotation annotation = Annotation.builder()
            .page((long) 10)
            .width((long) 100)
            .height((long) 100)
            .build();

        MockHttpServletResponse response = mvc.perform(post("/annotationSets",annotation)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();
//            .andExpect(status().isBadRequest());

        System.out.println("\n\n\n\n\n\n" + response.getContentAsString() + "\n\n\n\n\n\n");

        Assert.assertEquals(400,response.getStatus());

    }

    @Test
    public void postAnnotationNoBody() throws Exception {
        mvc.perform(post("/annotationSets")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }



}
