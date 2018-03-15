package uk.gov.hmcts.reform.em.annotation.functional

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
class MappingUrlIT extends BaseIT {

    @Test
    void "Normal Mappings"() {
        annotationProvider.givenAnnotationApiRequest()
            .expect()
            .statusCode(200)
            .when()
            .get('/mappings')
    }

}
