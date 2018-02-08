package uk.gov.hmcts.reform.em.annotation.componenttests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService.USER_ID_HEADER;
import static uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService.USER_ROLES_HEADER;

public class Helper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Helper(){}

//    CONSTANT VARIABLES

    public static final Annotation GOOD_ANNOTATION = Annotation.builder()
        .page((long) 10)
        .width((long) 100)
        .height((long) 100)
        .build();

    public static final AnnotationSet GOOD_ANNOTATION_SET = AnnotationSet.builder()
        .documentUri("https://localhost:4603/documents/" + UUID.randomUUID())
        .build();

    public static final AnnotationSet GOOD_ANNOTATION_SET_WITH_ANNOTATION = AnnotationSet.builder()
        .documentUri("https://localhost:4603/documents/" + UUID.randomUUID())
        .annotations(Collections.singleton(GOOD_ANNOTATION))
        .build();

    public static final AnnotationSet BAD_ANNOTATION_SET_MISSING_DOC_URI = AnnotationSet.builder().build();

    public static final String BAD_ANNOTATION_SET_EMPTY_BODY = "";

    public static final String BAD_ANNOTATION_SET_MALFORMED_BODY = "{ \\daksfakbl'':asbahfj}";

    public static final String ANNOTATION_SET_ENDPOINT = "annotation-sets";

//    REUSABLE METHODS

    public static String getSelfUrlFromResponse(MockHttpServletResponse response) throws IOException {
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
        System.out.println("===============" + path + "===============\n\n\n" + content);
        return MAPPER
                .readTree(content)
                .at("/" + path);
    }

    public static HttpHeaders getHeaders() {
        return getHeaders("user");
    }

    public static HttpHeaders getHeaders(String user) {
        final HttpHeaders headers = new HttpHeaders();
        if ("userCaseWorker".equals(user)) {
            headers.putAll(
                ImmutableMap.of(
                    "Authorization", Collections.singletonList(user),
                    "ServiceAuthorization", Collections.singletonList("sscs"),
                    USER_ID_HEADER, Collections.singletonList(user),
                    USER_ROLES_HEADER, Collections.singletonList("caseworker-probate")
                )
            );
        } else {
            headers.putAll(
                ImmutableMap.of(
                    "Authorization", Collections.singletonList(user),
                    "ServiceAuthorization", Collections.singletonList("sscs"),
                    USER_ID_HEADER, Collections.singletonList(user),
                    USER_ROLES_HEADER, Collections.singletonList("citizen")
                )
            );
        }
        return headers;
    }
}
