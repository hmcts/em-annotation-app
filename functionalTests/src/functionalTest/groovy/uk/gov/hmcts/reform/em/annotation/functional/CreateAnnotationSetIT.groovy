package uk.gov.hmcts.reform.em.annotation.functional

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet

@RunWith(SpringRunner.class)
class CreateAnnotationSetIT extends BaseIT {

    @Test
    void "ANB1 As a authenticated user and an owner of the document I can create a new annotation" () {

        def request = annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
            .body(annotationProvider.buildCompleteAnnotationSet(CITIZEN))

        request
            .expect()
                .statusCode(201)
            .when()
                .post("/annotation-sets")

    }

    @Test
    void "ANB2 As a caseworker I can create a new annotation" () {

        def request = annotationProvider
            .givenAnnotationApiRequest(CITIZEN, ["caseworker-probate"])
            .body(annotationProvider.buildCompleteAnnotationSet(CITIZEN))

        request
            .expect()
            .statusCode(201)
            .when()
            .post("/annotation-sets")

    }

    @Test
    void "ANB3 As a unauthenticated user I should get StatusCode 401" () {

        def request = annotationProvider
            .givenAnnotationApiRequest()
            .body(annotationProvider.buildCompleteAnnotationSet(CITIZEN))

        request
            .expect()
                .statusCode(403)
            .when()
                .post("/annotation-sets")

    }

    @Test
    void "ANB4 As a authenticated user, but not an owner of the document nor a caseworker I should get StatusCode 403" () {

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
                .body(annotationProvider.buildCompleteAnnotationSet(CITIZEN_2))
            .expect()
                .statusCode(403)
            .when()
                .post("/annotation-sets")

    }

    @Test
    void "ANB5 As a authenticated user, when I add the annotations with invalid header I should get StatusCode 406" () {

        //?

    }

    @Test
    void "ANB6 As a authenticated user, when I add the annotation to invalid Document URL I should get StatusCode 422"() {
        annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
                .body(AnnotationSet.builder().documentUri('https://www.google.com').build())
            .expect()
                .statusCode(422)
            .when()
                .post("/annotation-sets")
    }

    @Test
    void "ANB7 As a authenticated user, when I load the document annotations I get 200"() {

        String annotationUrl = annotationProvider.createAnnotationSetAndGetUrlAs CITIZEN

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
            .expect()
                .statusCode(200)
            .when()
                .get(annotationUrl.toURL())
    }

    @Test
    void "ANB8 As a caseworker, when I load the document annotations I get 200"() {

        String annotationUrl = annotationProvider.createAnnotationSetAndGetUrlAs CITIZEN

        annotationProvider
            .givenAnnotationApiRequest(CASEWORKER, ['caseworker-probate'])
            .expect()
                .statusCode(200)
            .when()
                .get(annotationUrl.toURL())
    }

    @Test
    void "ANB9 As a unauthenticated user, when I load the document annotations I get 403"() {

        String annotationUrl = annotationProvider.createAnnotationSetAndGetUrlAs CITIZEN

        annotationProvider
            .givenAnnotationApiRequest()
            .expect()
            .statusCode(403)
            .when()
            .get(annotationUrl.toURL())
    }

    @Test
    void "ANB10 As a authenticated user but not the owner, when I load the document annotations I get 403"() {
        String annotationUrl = annotationProvider.createAnnotationSetAndGetUrlAs CITIZEN

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN_2)
            .expect()
            .statusCode(403)
            .when()
            .get(annotationUrl.toURL())
    }

    @Test
    void "ANB11 As a authenticated user, when I GET the annotations with invalid header I should get StatusCode 406"() {
        //?
    }

    @Test
    void "ANB12 As a authenticated user, when I DELETE the document annotations I get 204" () {

        String annotationUrl = annotationProvider.createAnnotationSetAndGetUrlAs CITIZEN

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
                .expect()
            .statusCode(204)
                .when()
            .delete(annotationUrl.toURL())

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
                .expect()
            .statusCode(404)
            .when()
                .get(annotationUrl.toURL())
    }
}
