package uk.gov.hmcts.reform.em.annotation.componenttests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import static uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService.USER_ID_HEADER;
import static uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService.USER_ROLES_HEADER;

public class Helper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Helper(){}


    public static String getUuid(MockHttpServletResponse response) throws IOException {
        final String path = "uuid";
        return getAnnotationSetUrl(response, path);
    }

    public static String getAnnotationSetUrl(MockHttpServletResponse response, String path) throws IOException {
        final String content = response.getContentAsString();
        return "/annotationSets/" + getNodeAtPath(path, content).asText();
    }


    public static String getThumbnailUrlFromResponse(MockHttpServletResponse response) throws IOException {
        final String path = "/_links/thumbnail/href";
        return getPathFromResponse(response, path);

    }

    public static String getBinaryUrlFromResponse(MockHttpServletResponse response) throws IOException {
        final String path = "/_links/binary/href";
        return getPathFromResponse(response, path);

    }

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
        System.out.println(path + "    -------------- " + content);

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
