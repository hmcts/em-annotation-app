package uk.gov.hmcts.reform.em.annotation.actuate.health.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HealthCheckResponse {
    private String status;
}
