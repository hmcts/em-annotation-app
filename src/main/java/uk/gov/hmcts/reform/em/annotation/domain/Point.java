package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Point {

    @NotNull
    private Long x;
    @NotNull
    private Long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("[%s,%s]",x,y);
    }


//    [x,y]
}
