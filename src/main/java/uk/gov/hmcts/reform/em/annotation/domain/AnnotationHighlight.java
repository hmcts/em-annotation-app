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
public class AnnotationHighlight extends Annotation {
    AnnotationHighlight(UUID id, String createdBy, String lastModifiedBy, Date modifiedOn, Date createdOn, String className, String type, long page, UUID uuid, String colour, long width, List<Point> lines, List<Rectangle> rectangles, long x, long y, long size, long height) {
        super(id, createdBy, lastModifiedBy, modifiedOn, createdOn, className, type, page, uuid, colour, width, lines, rectangles, x, y, size, height);
    }


//    private String className = "Annotation";
//    private String type = "highlight";
//    private long page;
//    private UUID uuid;
//    private String colour;
//    private List<Rectangle> rectangles;



    //    Highlight
//    {
//        "class": "Annotation",
//        "type": "highlight",
//        "page": 1,
//        "uuid": "99c84974-b899-4de9-8c6c-28e541c03db8",
//        "color": "FFFF00",
//        "rectangles": [
//        {
//            "height": 12,
//            "width": 335,
//            "x": 188,
//            "y": 189
//        },
//        {
//            "height": 12,
//            "width": 431,
//            "x": 72,
//            "y": 205
//        }
//  ]
//    }
}
