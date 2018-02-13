package uk.gov.hmcts.reform.em.annotation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware",
    dateTimeProviderRef = "dateTimeService")
public class JpaConfig {

}
