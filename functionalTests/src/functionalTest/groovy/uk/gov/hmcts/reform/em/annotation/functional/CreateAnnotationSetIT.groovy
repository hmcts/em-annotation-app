package uk.gov.hmcts.reform.em.annotation.functional

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
class CreateAnnotationSetIT extends BaseIT {

    @Test
    void "AN1 As a user I can Annotate Page 1 of a PDF"() {
        String dmResponse = docStoreProvider.createDocumentAndGetUrlAs(CITIZEN)
        print(dmResponse)

        emAnnoResponse = annotationProvider.createAnnoationSet(CITIZEN)
        print(emAnnoResponse)
//        Given that I have Uploaded to DM Store App
//        When I Create An AnnotationSet
//        Then Return 200
//        And Body Contains:
//        Correct Document URI
//        Correct Annotations
    }

}
