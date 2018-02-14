package uk.gov.hmcts.reform.em.annotation.componenttests.backdoors;

import uk.gov.hmcts.reform.auth.checker.core.SubjectResolver;
import uk.gov.hmcts.reform.auth.checker.core.exceptions.AuthCheckerException;
import uk.gov.hmcts.reform.auth.checker.core.service.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceResolverBackdoor implements SubjectResolver<Service> {

    private final ConcurrentHashMap<String, String> tokenToServiceMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        registerToken("em_gw", "em_gw");
    }

    @Override
    public Service getTokenDetails(String token) {
        String serviceId = tokenToServiceMap.get(token);

        if (serviceId == null) {
            throw new AuthCheckerException("Token not found");
        }

        return new Service(serviceId);
    }

    public void registerToken(String token, String serviceId) {
        tokenToServiceMap.put(token, serviceId);
    }
}
