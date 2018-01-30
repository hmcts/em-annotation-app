package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
//@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnnotationPoint extends Annotation {
    AnnotationPoint(UUID id, String createdBy, String lastModifiedBy, Date modifiedOn, Date createdOn, String className, String type, long page, UUID uuid, String colour, long width, List<Point> lines, List<Rectangle> rectangles, long x, long y, long size, long height) {
        super(id, createdBy, lastModifiedBy, modifiedOn, createdOn, className, type, page, uuid, colour, width, lines, rectangles, x, y, size, height);
    }

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
