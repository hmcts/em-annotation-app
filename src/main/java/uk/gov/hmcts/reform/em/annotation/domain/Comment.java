package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment extends AbstractAnnotation {

//    private static final AnnotationClassType className = AnnotationClassType.COMMENT;
//    private UUID uuid;
    private UUID annotationUuid;
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
