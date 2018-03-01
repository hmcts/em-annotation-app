package uk.gov.hmcts.reform.em.annotation.functional

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

import javax.annotation.PostConstruct

@RunWith(SpringRunner.class)
class MappingUrlIT extends BaseIT {

//    @Value('${toggle.thumbnail}')
//    boolean thumbnail

    def request

    @PostConstruct
    void init(){
        request = givenUnauthenticatedRequest().get("/mappings").path('')
    }

    @Test
    void "Normal Mappings"() {
      assert true
    }

}
