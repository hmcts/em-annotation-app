package uk.gov.hmcts.reform.em.annotation.domain.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService;

@Service("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    private SecurityUtilService securityUtilService;

    @Autowired
    public AuditorAwareImpl(SecurityUtilService securityUtilService) {
        this.securityUtilService = securityUtilService;
    }

    @Override
    public String getCurrentAuditor() {
        return securityUtilService.getUserId();
    }
}
