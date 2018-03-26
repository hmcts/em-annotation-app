package uk.gov.hmcts.reform.em.annotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Rectangle {

    @Id
    @Getter
    @Setter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    private Annotation annotation;

    @Getter
    @Setter
    @NotNull
    private Long height;

    @Getter
    @Setter
    @NotNull
    private Long width;

    @Getter
    @Setter
    @NotNull
    private Long pointX;

    @Getter
    @Setter
    @NotNull
    private Long pointY;

    public void setX(Long x) {
        setPointX(x);
    }

    public void setY(Long y) {
        setPointY(y);
    }

    public Long getX() {
        return getPointX();
    }
    public Long getY() {
        return getPointY();
    }
    @Override
    public String toString() {
        return String.format(
            "{\\n\"height\": %n,\\n\"width\": %n,\\n\"x\": %n,\\n\"y\": %n\\n }",
            height,width,pointX,pointY);
    }
}
