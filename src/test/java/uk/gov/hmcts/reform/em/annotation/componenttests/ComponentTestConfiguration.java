package uk.gov.hmcts.reform.em.annotation.componenttests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import uk.gov.hmcts.reform.auth.checker.core.RequestAuthorizer;
import uk.gov.hmcts.reform.auth.checker.core.SubjectResolver;
import uk.gov.hmcts.reform.auth.checker.core.service.Service;
import uk.gov.hmcts.reform.auth.checker.core.user.User;
import uk.gov.hmcts.reform.auth.checker.spring.serviceonly.AuthCheckerServiceOnlyFilter;
import uk.gov.hmcts.reform.em.annotation.componenttests.backdoors.ServiceResolverBackdoor;
import uk.gov.hmcts.reform.em.annotation.componenttests.backdoors.UserResolverBackdoor;

@Configuration
public class ComponentTestConfiguration {

    @Bean
    public SubjectResolver<Service> serviceResolver() {
        return new ServiceResolverBackdoor();
    }

    @Bean
    public SubjectResolver<User> userResolver() {
        return new UserResolverBackdoor();
    }

    @Bean
    public AuthCheckerServiceOnlyFilter authCheckerServiceFilter(RequestAuthorizer<Service> serviceRequestAuthorizer,
                                                                 AuthenticationManager authenticationManager) {
        AuthCheckerServiceOnlyFilter filter = new AuthCheckerServiceOnlyFilter(serviceRequestAuthorizer);
        filter.setAuthenticationManager(authenticationManager);
        filter.setCheckForPrincipalChanges(true);
        filter.setInvalidateSessionOnPrincipalChange(true);
        return filter;
    }

}
