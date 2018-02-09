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
public class Comment {

//    private static final AnnotationClassType className = AnnotationClassType.COMMENT;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;
//    private UUID annotationUuid;

    @Getter
    @Setter
    @ManyToOne
    @JsonIgnore
    private Annotation annotation;

    @Getter
    @Setter
    @NotNull
    private String content;


//    Point
//    {
//        "class": "Annotation",
//        "type": "point",
//        "page": 1,
//        "uuid": "e101a5aa-0a85-4b60-86ff-bcf411a1f7f3",
//        "x": 150,
//        "y": 135
//    },
//    {
//        "class": "Comment",
//        "uuid": "a9501784-b7a8-4c5b-8243-d7c93ce9dc79",
//        "annotation": "e101a5aa-0a85-4b60-86ff-bcf411a1f7f3",
//        "content": "This is a comment"
//    }

}
