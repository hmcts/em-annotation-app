package uk.gov.hmcts.reform.em.annotation.functional.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import uk.gov.hmcts.reform.em.annotation.domain.*
import uk.gov.hmcts.reform.em.annotation.functional.AuthTokenProvider

import static io.restassured.RestAssured.given

@Service
class AnnotationProvider {

    String emAnnoAppBaseUri

    AuthTokenProvider authTokenProvider

    String DOCUMENT_URI = "https://localhost:4603/documents/" + UUID.randomUUID()

    Annotation GOOD_ANNOTATION_MINIMUM = Annotation.builder().build()

    Annotation GOOD_ANNOTATION = Annotation.builder()
        .page((long) 10)
        .width((long) 100)
        .height((long) 100)
        .build()

    Annotation GOOD_ANNOTATION_COMPLETE = Annotation.builder()
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
        .build()

    AnnotationSet GOOD_ANNOTATION_SET_MINIMUM = AnnotationSet.builder()
        .documentUri(DOCUMENT_URI)
        .build()

    AnnotationSet GOOD_ANNOTATION_SET_WITH_ANNOTATION = AnnotationSet.builder()
        .documentUri(DOCUMENT_URI)
        .annotations(Collections.singleton(GOOD_ANNOTATION))
        .build()

    AnnotationSet GOOD_ANNOTATION_SET_COMPLETE = AnnotationSet.builder()
        .documentUri(DOCUMENT_URI)
        .annotations(
        ImmutableSet.of(
            GOOD_ANNOTATION_MINIMUM,
            GOOD_ANNOTATION,
            GOOD_ANNOTATION_COMPLETE
        )
    )
        .build()

    AnnotationSet BAD_ANNOTATION_SET_MISSING_DOC_URI = AnnotationSet.builder().build()

    String GOOD_ANNOTATION_STR
    String GOOD_ANNOTATION_SET_COMPLETE_STR
    String GOOD_ANNOTATION_SET_MINIMUM_STR
    String GOOD_ANNOTATION_SET_WITH_ANNOTATION_STR
    String BAD_ANNOTATION_SET_MISSING_DOC_URI_STR

    @Autowired
    AnnotationProvider(
        AuthTokenProvider authTokenProvider,
        @Value('${base-urls.em-anno-app}') String emAnnoAppBaseUri
    ) {
        this.emAnnoAppBaseUri = emAnnoAppBaseUri
        this.authTokenProvider = authTokenProvider
        System.out.println("EM ANNO APP URL - " + emAnnoAppBaseUri)

        GOOD_ANNOTATION_STR = convertObjectToJsonString(GOOD_ANNOTATION)
        GOOD_ANNOTATION_SET_COMPLETE_STR = convertObjectToJsonString(GOOD_ANNOTATION_SET_COMPLETE)
        GOOD_ANNOTATION_SET_MINIMUM_STR = convertObjectToJsonString(GOOD_ANNOTATION_SET_MINIMUM)
        GOOD_ANNOTATION_SET_WITH_ANNOTATION_STR = convertObjectToJsonString(GOOD_ANNOTATION_SET_WITH_ANNOTATION)
        BAD_ANNOTATION_SET_MISSING_DOC_URI_STR = convertObjectToJsonString(BAD_ANNOTATION_SET_MISSING_DOC_URI)

    }

    def createAnnoationSetAndGetUrlAs(username, annotationSet=null) {
        createAnnotationSet(username, filename, annotationSet)
            .path("_embedded.annotationset[0]._links.self.href")
    }

    def createAnnotationSet(username, annotationSet=null) {
        def request = givenEMAnnoRequest(username)
            .log().all()
            .contentType('application/json')
            .body(annotationSet)

        request
            .expect()
            .statusCode(201)
            .when()
            .post("/annotation-sets")
    }

    def givenEMAnnoRequest(username = null, userRoles = null) {
        def request =  given().baseUri(emAnnoAppBaseUri).log().all().contentType('application/json')
        if (username) {
            request = request.header("serviceauthorization", serviceToken())
            if (username) {
                request = request.header("user-id", username)
            }
            if (userRoles) {
                request = request.header("user-roles", userRoles.join(','))
            }
        }
        request
    }

    def givenEmAnnotationUnauthenticatedRequest() {
        given().baseUri(emAnnoAppBaseUri).log().all().contentType('application/json')
    }

    def serviceToken() {
        authTokenProvider.findServiceToken()
    }


    static String convertObjectToJsonString(Object object) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter()
        return ow.writeValueAsString(object)
    }
}
