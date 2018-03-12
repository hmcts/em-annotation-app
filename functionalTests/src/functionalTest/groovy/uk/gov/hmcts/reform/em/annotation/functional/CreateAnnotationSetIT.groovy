package uk.gov.hmcts.reform.em.annotation.functional

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
class CreateAnnotationSetIT extends BaseIT {

    @Test
    void "ANB1 As a authenticated user and an owner of the document I can create a new annotation" () {

        def request = annotationProvider
            .givenEMAnnoRequest(CITIZEN)
            .body(annotationProvider.GOOD_ANNOTATION_SET_COMPLETE)

        request
            .expect()
                .statusCode(201)
            .when()
                .post("/annotation-sets")

    }

    @Test
    void "ANB2 As a caseworker I can create a new annotation" () {

        def request = annotationProvider
            .givenEMAnnoRequest(CITIZEN, ["caseworker-probate"])
            .body(annotationProvider.GOOD_ANNOTATION_SET_COMPLETE)

        request
            .expect()
            .statusCode(201)
            .when()
            .post("/annotation-sets")

    }

    @Test
    void "ANB3 As a unauthenticated user I should get StatusCode 401" () {

        def request = annotationProvider
            .givenEmAnnotationUnauthenticatedRequest()
            .body(annotationProvider.GOOD_ANNOTATION_SET_COMPLETE)

        request
            .expect()
                .statusCode(403)
            .when()
                .post("/annotation-sets")

    }

    @Test
    void "ANB4 As a authenticated user, but not an owner of the document I should get StatusCode 403" () {

        def request = annotationProvider
            .givenEMAnnoRequest(CITIZEN)
            .body(annotationProvider.GOOD_ANNOTATION_SET_COMPLETE)

        request
            .expect()
            .statusCode(403)
            .when()
            .post("/annotation-sets")

    }
}
