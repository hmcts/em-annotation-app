package uk.gov.hmcts.reform.em.annotation.componenttests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;
import uk.gov.hmcts.reform.em.annotation.domain.*;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import static uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService.USER_ID_HEADER;
import static uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService.USER_ROLES_HEADER;

public class Helper {

    private static final ObjectMapper MAPPER = new ObjectMapper();


//    CONSTANT VARIABLES

    public static final String DOCUMENT_URI = "https://localhost:4603/documents/" + UUID.randomUUID();

    public static final Annotation GOOD_ANNOTATION_MINIMUM = Annotation.builder().build();

    public static final Annotation GOOD_ANNOTATION = Annotation.builder()
        .page((long) 10)
        .width((long) 100)
        .height((long) 100)
        .build();

    public static final Annotation GOOD_ANNOTATION_COMPLETE = Annotation.builder()
        .page(0)
        .type(AnnotationType.POINT)
        .colour("FFFFFF")
        .height((long) 0)
        .width((long) 10)
        .pointX((long) 0)
        .pointY((long) 1)
        .comments(ImmutableSet.of(
            Comment.builder()
                .content("some text")
                .build(),
            Comment.builder()
                .content("some more text")
                .build(),
            Comment.builder()
                .content("this is not a comment")
                .build()
        ))
        .lines(
            ImmutableList.of(
                Point.builder()
                    .pointX((long) 0)
                    .pointY((long) 1)
                    .build(),
                Point.builder()
                    .pointX((long) 10)
                    .pointY((long) 20)
                    .build()
            )
        )
        .rectangles(ImmutableSet.of(
            Rectangle.builder()
                .height((long) 0)
                .width((long) 10)
                .pointX((long) 0)
                .pointY((long) 1)
                .build(),
            Rectangle.builder()
                .height((long) 5)
                .width((long) 20)
                .pointX((long) 5)
                .pointY((long) 30)
                .build()
        ))
        .build();

    public static final AnnotationSet GOOD_ANNOTATION_SET_MINIMUM = AnnotationSet.builder()
        .documentUri(DOCUMENT_URI)
        .build();

    public static final AnnotationSet GOOD_ANNOTATION_SET_WITH_ANNOTATION = AnnotationSet.builder()
        .documentUri(DOCUMENT_URI)
        .annotations(Collections.singleton(GOOD_ANNOTATION))
        .build();

    public static final AnnotationSet GOOD_ANNOTATION_SET_COMPLETE = AnnotationSet.builder()
        .documentUri(DOCUMENT_URI)
        .annotations(
            ImmutableSet.of(
                GOOD_ANNOTATION_MINIMUM,
                GOOD_ANNOTATION,
                GOOD_ANNOTATION_COMPLETE
            )
        )
        .build();

    public static final AnnotationSet BAD_ANNOTATION_SET_MISSING_DOC_URI = AnnotationSet.builder().build();

    public static String GOOD_ANNOTATION_STR;
    public static String GOOD_ANNOTATION_SET_COMPLETE_STR;
    public static String GOOD_ANNOTATION_SET_MINIMUM_STR;
    public static String GOOD_ANNOTATION_SET_WITH_ANNOTATION_STR;
    public static String BAD_ANNOTATION_SET_MISSING_DOC_URI_STR;

    static {
        try {
            GOOD_ANNOTATION_STR = convertObjectToJsonString(GOOD_ANNOTATION);
            GOOD_ANNOTATION_SET_COMPLETE_STR = convertObjectToJsonString(GOOD_ANNOTATION_SET_COMPLETE);
            GOOD_ANNOTATION_SET_MINIMUM_STR = convertObjectToJsonString(GOOD_ANNOTATION_SET_MINIMUM);
            GOOD_ANNOTATION_SET_WITH_ANNOTATION_STR = convertObjectToJsonString(GOOD_ANNOTATION_SET_WITH_ANNOTATION);
            BAD_ANNOTATION_SET_MISSING_DOC_URI_STR = convertObjectToJsonString(BAD_ANNOTATION_SET_MISSING_DOC_URI);
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }


    public static final String BAD_ANNOTATION_SET_EMPTY_BODY_STR = "";

    public static final String BAD_ANNOTATION_SET_MALFORMED_BODY_STR = "{ \\daksfakbl'':asbahfj}";

    public static final String ANNOTATION_SET_ENDPOINT = "/annotation-sets/";

    public static final String ANNOTATION_FIND_ALL_BY_DOCUMENT_URL_ENDPOINT = ANNOTATION_SET_ENDPOINT + "filter";
    public static final String URL_PARAM = "url";



    private Helper() {}

//    REUSABLE METHODS

    public static String getSelfUrlFromResponse(MockHttpServletResponse response) throws IOException {
        final String path = "_links/self/href";
        return getPathFromResponse(response, path);
    }

    private static String getPathFromResponse(MockHttpServletResponse response, String path) throws IOException {
        final String content = response.getContentAsString();
        return getNodeAtPath(path, content).asText().replace("http://localhost", "");
    }

    private static JsonNode getNodeAtPath(String path, String content) throws IOException {
        return MAPPER.readTree(content).at("/" + path);
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
                    "ServiceAuthorization", Collections.singletonList("em_gw"),
                    USER_ID_HEADER, Collections.singletonList(user),
                    USER_ROLES_HEADER, Collections.singletonList("caseworker-probate")
                )
            );
        } else {
            headers.putAll(
                ImmutableMap.of(
                    "Authorization", Collections.singletonList(user),
                    "ServiceAuthorization", Collections.singletonList("em_gw"),
                    USER_ID_HEADER, Collections.singletonList(user),
                    USER_ROLES_HEADER, Collections.singletonList("citizen")
                )
            );
        }
        return headers;
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper om = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return om.writeValueAsBytes(object);
    }

    public static String convertObjectToJsonString(Object object) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}
