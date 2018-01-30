package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Rectangle {

    @NonNull
    private long height;
    @NonNull
    private long width;
    @NonNull
    private long x;
    @NonNull
    private long y;

    @Override
    public String toString() {
        return String.format(
            "{\\n\"height\": %n,\\n\"width\": %n,\\n\"x\": %n,\\n\"y\": %n\\n }",
            height,width,x,y);
    }

    //        {
//            "height": 12,
//            "width": 335,
//            "x": 188,
//            "y": 189
//        }

}
