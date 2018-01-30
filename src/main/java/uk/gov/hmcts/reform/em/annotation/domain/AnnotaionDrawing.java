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
public class AnnotaionDrawing extends Annotation {
    AnnotaionDrawing(UUID id, String createdBy, String lastModifiedBy, Date modifiedOn, Date createdOn, String className, String type, long page, UUID uuid, String colour, long width, List<Point> lines, List<Rectangle> rectangles, long x, long y, long size, long height) {
        super(id, createdBy, lastModifiedBy, modifiedOn, createdOn, className, type, page, uuid, colour, width, lines, rectangles, x, y, size, height);
    }

//    private String className = "Annotation";
//    private String type = "drawing";
//    private long page;
//    private UUID uuid;
//    private String colour;
//    private long width;
//    private List<Point> lines;

    //    Drawing
//    {
//        "class": "Annotation",
//        "type": "drawing",
//        "page": 1,
//        "uuid": "2748a2d6-4089-4f63-b3b8-a61910487bdb",
//        "color": "000000",
//        "width": 1,
//        "lines": [
//    [113, 81],
//    [115, 80],
//    [119, 79],
//    [123, 77],
//    [126, 75]
//  ]
//    }
}
