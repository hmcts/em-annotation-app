package uk.gov.hmcts.reform.em.annotation.domain.junk;

import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnnotationPoint {

    private long x;
    private long y;


//    private String className = "Annotation";
//    private String type = "point";
//    private long page;
//    private UUID uuid;
//    private long x;
//    private long y;

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
