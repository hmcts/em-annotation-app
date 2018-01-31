package uk.gov.hmcts.reform.em.annotation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
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
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.util.Date;
import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringRunner.class)
@ActiveProfiles({"embedded", "local", "componenttest"})
@SpringBootTest(webEnvironment = MOCK)
@Transactional
@EnableSpringDataWebSupport
@DirtiesContext
public class StoredAnnotationSetControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected RestActions restActions;

    @Autowired
    protected AuthCheckerServiceOnlyFilter filter;

    @Autowired
    protected ServiceResolverBackdoor serviceRequestAuthorizer;

    @Autowired
    protected UserResolverBackdoor userRequestAuthorizer;

    private MockMvc mvc;

    private HttpHeaders headers = Helper.getHeaders();

    @Before
    public void setUp() {
        mvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        this.restActions = new RestActions(mvc, serviceRequestAuthorizer, userRequestAuthorizer, objectMapper);
        filter.setCheckForPrincipalChanges(true);
        filter.setInvalidateSessionOnPrincipalChange(true);
    }

    @Test
    public void postAnnotationSet() throws Exception {
        String annotationSet = AnnotationSet.builder()
            .annotations(
                ImmutableSet.of(
                    Annotation.builder()
                        .page((long) 10)
                        .build())
            ).build().toString();

       mvc.perform(post("/annotationSets")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(annotationSet))
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
        String annotationSet = AnnotationSet.builder()
            .createdBy("Alec")
            .createdOn(new Date())
//            .uuid(UUID.randomUUID())
            .annotations(
                ImmutableSet.of(
                    Annotation.builder()
                        .page((long) 10)
                        .build(),
                    Annotation.builder()
                        .page((long) 10)
                        .build()
                )
            ).build().toString();

        mvc.perform(post("/annotationSets")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(annotationSet))
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
    @Ignore("need to fix")
    public void postAnnotatioWrongObject() throws Exception {
        String annotationSet = Annotation.builder()
            .page((long) 10)
            .width((long) 100)
            .height((long) 100)
            .build().toString();

        MockHttpServletResponse response = mvc.perform(post("/annotationSets")
            .headers(headers)
            .contentType(MediaType.APPLICATION_JSON)
            .content(annotationSet))
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
