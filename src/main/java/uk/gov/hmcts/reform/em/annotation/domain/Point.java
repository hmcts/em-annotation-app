package uk.gov.hmcts.reform.em.annotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Point {

    @Id
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
    private Long pointX;

    @Getter
    @Setter
    @NotNull
    private Long pointY;

    public String toString() {
        return String.format("[%s,%s]",pointX,pointY);
    }


//    [x,y]
}
