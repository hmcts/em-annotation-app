package uk.gov.hmcts.reform.em.annotation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import uk.gov.hmcts.reform.em.annotation.componenttests.sugar.CustomResultMatcher;
import uk.gov.hmcts.reform.em.annotation.componenttests.sugar.RestActions;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static uk.gov.hmcts.reform.em.annotation.componenttests.Helper.*;

@RunWith(SpringRunner.class)
@ActiveProfiles({"local"})
@SpringBootTest(webEnvironment = MOCK)
@Transactional
@EnableSpringDataWebSupport
@DirtiesContext
public class AnnotationSetSearchControllerTest {

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


    String resolvePlaceholders(String content) {
        return configurableListableBeanFactory.resolveEmbeddedValue(content);
    }


    @Test
    public void should_upload_empty_annotation_set_and_retive_annotation_set() throws Exception {
        mvc.perform(post(Helper.ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        mvc.perform(get(ANNOTATION_FIND_ALL_BY_DOCUMENT_URL_ENDPOINT)
            .headers(headers)
            .param(URL_PARAM, GOOD_ANNOTATION_SET_MINIMUM.getDocumentUri()))
            .andExpect(status().isOk())
            .andReturn().getResponse();
    }

    @Test
    public void should_upload_empty_annotation_set_and_retive_annotation_set2() throws Exception {

        final MockHttpServletResponse response1 = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andReturn().getResponse();

        final MockHttpServletResponse response2 = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_COMPLETE_STR))
            .andReturn().getResponse();

        final String url1 = getSelfUrlFromResponse(response1);
        final String url2 = getSelfUrlFromResponse(response2);

        final MockHttpServletResponse getResp = mvc.perform(get(ANNOTATION_FIND_ALL_BY_DOCUMENT_URL_ENDPOINT)
            .headers(headers)
            .param(URL_PARAM, DOCUMENT_URI))
            .andExpect(status().isOk())
            .andReturn().getResponse();

    }
}
