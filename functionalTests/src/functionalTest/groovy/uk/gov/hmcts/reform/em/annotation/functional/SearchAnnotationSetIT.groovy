package uk.gov.hmcts.reform.em.annotation.functional

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import uk.gov.hmcts.reform.em.annotation.domain.Annotation
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.isEmptyOrNullString

@RunWith(SpringRunner.class)
class SearchAnnotationSetIT extends BaseIT {

    @Test
    void "ANB17 I can find AnnotationSets by a document URL by I owned search documents (sorted by date created desc)" () {

        AnnotationSet annotationSet = annotationProvider.buildCompleteAnnotationSet CITIZEN

        def response1 = annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
            .body(annotationSet)
            .expect()
                .statusCode(201)
            .when()
                .post("/annotation-sets")

        def annotationUrl1 = response1.path("_links.self.href")

        def response2 = annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
            .body(annotationSet)
            .expect()
                .statusCode(201)
            .when()
                .post("/annotation-sets")

        def annotationUrl2 = response2.path("_links.self.href")

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN_2)
            .body(annotationSet)
            .expect()
                .statusCode(201)
            .when()
             .post("/annotation-sets")

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
            .expect()
                .statusCode(200)
                .body("_embedded.annotationSets[0]._links.self.href", equalTo(annotationUrl2))
                .body("_embedded.annotationSets[1]._links.self.href", equalTo(annotationUrl1))
                .body("page.size", equalTo(5))
                .body("page.totalElements", equalTo(2))
                .body("page.totalPages", equalTo(1))
            .when()
                .get("/annotation-sets/filter?url=$annotationSet.documentUri")

    }

    @Test
    void "ANB18 I get 403 if I'm unauthenticated (s2s)" () {

        annotationProvider
            .givenAnnotationApiRequest()
            .expect()
                .statusCode(403)
            .when()
                .get("/annotation-sets/filter?url=https://www.google.com")

    }

    @Test
    void "ANB19 I can't find Annotation Sets that I don't own" () {

        AnnotationSet annotationSet = annotationProvider.buildCompleteAnnotationSet CITIZEN

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN)
            .body(annotationSet)
            .expect()
                .statusCode(201)
            .when()
                .post("/annotation-sets")

        annotationProvider
            .givenAnnotationApiRequest(CITIZEN_2)
            .expect()
                .statusCode(200)
                .body("_embedded.annotationSets", isEmptyOrNullString())
                .body("page.size", equalTo(5))
                .body("page.totalElements", equalTo(0))
                .body("page.totalPages", equalTo(0))
            .when()
                .get("/annotation-sets/filter?url=$annotationSet.documentUri")

    }

}
