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
public class Rectangle {

    @NotNull
    private Long height;

    @NotNull
    private Long width;

    @NotNull
    private Long x;

    @NotNull
    private Long y;

    public Rectangle(long height, long width, long x, long y) {
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

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
