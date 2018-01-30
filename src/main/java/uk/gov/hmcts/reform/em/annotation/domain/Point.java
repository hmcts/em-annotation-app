package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Point {

    private long x;
    private long y;

    public String toString() {
        return String.format("[%s,%s]",x,y);
    }


//    [x,y]
}
