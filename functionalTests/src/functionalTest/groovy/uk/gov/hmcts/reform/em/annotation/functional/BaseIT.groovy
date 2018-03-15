package uk.gov.hmcts.reform.em.annotation.functional

import net.jcip.annotations.NotThreadSafe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import uk.gov.hmcts.reform.em.annotation.functional.config.FunctionalTestContextConfiguration
import uk.gov.hmcts.reform.em.annotation.functional.service.AnnotationProvider
import uk.gov.hmcts.reform.em.annotation.functional.service.DocumentStoreProvider

@ContextConfiguration(classes = FunctionalTestContextConfiguration)
@NotThreadSafe
class BaseIT {

    @Autowired
    DocumentStoreProvider docStoreProvider

    @Autowired
    AnnotationProvider annotationProvider

    String CITIZEN = 'test12@test.com'

    String CITIZEN_2 = 'test22@test.com'

    String CASEWORKER = 'caseworker-probate'



}
