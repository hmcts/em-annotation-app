package uk.gov.hmcts.reform.em.annotation.smoke

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
class MappingUrlIT extends BaseIT {

//    @Value('${toggle.thumbnail}')
//    boolean thumbnail

    @Test
    void "Normal Mappings"() {
        givenUnauthenticatedRequest()
            .expect()
            .statusCode(200)
            .when()
            .get('/mappings')
    }

}
