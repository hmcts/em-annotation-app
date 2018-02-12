package uk.gov.hmcts.reform.em.annotation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
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

import java.util.UUID;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static uk.gov.hmcts.reform.em.annotation.componenttests.Helper.*;


@RunWith(SpringRunner.class)
@ActiveProfiles({"local"})
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

    CustomResultMatcher body() {
        return new CustomResultMatcher(objectMapper);
    }


    String resolvePlaceholders(String content) {
        return configurableListableBeanFactory.resolveEmbeddedValue(content);
    }



//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////POST/////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////

    @Test
    public void postAnnotationSetOkMinimum() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated());
    }

    @Test
    public void postAnnotationSetOkWithAudit() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.createdBy").value("user"))
            .andExpect(jsonPath("$.lastModifiedBy").value("user"))
            .andExpect(jsonPath("$.createdOn").value(notNullValue()))
            .andExpect(jsonPath("$.modifiedOn").value(notNullValue()));
    }

    @Test
    public void postAnnotationSetOkWithAnnotation() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_WITH_ANNOTATION_STR))
            .andExpect(status().isCreated());
    }

    @Test
    public void postAnnotationSetBadRequestMissingDocumentUri() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(BAD_ANNOTATION_SET_MISSING_DOC_URI_STR))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postAnnotationSetBadRequestEmptyBody() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(BAD_ANNOTATION_SET_EMPTY_BODY_STR))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postAnnotationBadRequestMalformedBody() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(BAD_ANNOTATION_SET_MALFORMED_BODY_STR))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postAnnotationBadRequestWrongObject() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_STR))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postAnnotationBadRequestNoBody() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void postAnnotationForbiddenNoBodyNoUser() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden());
    }

    @Test
    public void postAnnotationForbiddenNoUser() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isForbidden());
    }

//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////GET/////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////

    @Test
    public void getAnnotationSetOk() throws Exception {
        final MockHttpServletResponse response = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        final String url = getSelfUrlFromResponse(response);

        mvc.perform(get(url)
            .headers(headers))
            .andExpect(status().isOk());
    }

    @Test
    public void getAnnotationSetOkCompleteBody() throws Exception {
        final MockHttpServletResponse response = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_COMPLETE_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        final String url = getSelfUrlFromResponse(response);

        mvc.perform(get(url)
            .headers(headers))
            .andExpect(status().isOk());
    }

    @Test
    public void getAnnotationSetForbidden() throws Exception {
        final MockHttpServletResponse response = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        final String url = getSelfUrlFromResponse(response);

        mvc.perform(get(url))
            .andExpect(status().isForbidden());
    }

    @Test
    public void getAnnotationSetNotFound() throws Exception {
        mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated());

        mvc.perform(get(ANNOTATION_SET_ENDPOINT + UUID.randomUUID())
            .headers(headers))
            .andExpect(status().isNotFound())
            .andReturn().getResponse();
    }

//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////PUT/////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////

    @Test
    public void putAnnotationSetOk() throws Exception {
        final MockHttpServletResponse response = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        final String url = getSelfUrlFromResponse(response);

        mvc.perform(put(url)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_COMPLETE_STR))
            .andExpect(status().isOk());
    }

    @Test
    public void putAnnotationSetFForbiddenNoBodyNoUser() throws Exception {
        final MockHttpServletResponse response = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        final String url = getSelfUrlFromResponse(response);

        mvc.perform(put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_COMPLETE_STR))
            .andExpect(status().isForbidden());
    }

    @Test
    public void putAnnotationSetNotFound() throws Exception {
        mvc.perform(put(ANNOTATION_SET_ENDPOINT + UUID.randomUUID())
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isNotFound());
    }

//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////DELETE//////////////////////////////////////////////////
//////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////

    @Test
    public void deleteAnnotationSet() throws Exception {
        final MockHttpServletResponse response = mvc.perform(post(ANNOTATION_SET_ENDPOINT)
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(GOOD_ANNOTATION_SET_MINIMUM_STR))
            .andExpect(status().isCreated())
            .andReturn().getResponse();

        final String url = getSelfUrlFromResponse(response);

        mvc.perform(get(url)
            .headers(headers))
            .andExpect(status().isOk());

        mvc.perform(delete(url)
            .headers(headers))
            .andExpect(status().isNoContent());

        mvc.perform(get(url)
            .headers(headers))
            .andExpect(status().isNotFound());
    }

    @Test
    public void deleteAnnotationSetNoExistingEndpoint() throws Exception {
        mvc.perform(delete(ANNOTATION_SET_ENDPOINT + UUID.randomUUID())
            .headers(headers))
            .andExpect(status().isNoContent());
    }

}
