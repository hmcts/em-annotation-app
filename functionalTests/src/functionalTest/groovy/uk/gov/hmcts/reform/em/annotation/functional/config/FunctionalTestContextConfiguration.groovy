package uk.gov.hmcts.reform.em.annotation.functional.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:application-functional-test.yaml")
@ComponentScan("uk.gov.hmcts.reform.em.annotation.functional")
class FunctionalTestContextConfiguration {
}
