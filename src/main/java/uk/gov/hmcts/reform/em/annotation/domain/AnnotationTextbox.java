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
public class AnnotationTextbox extends Annotation {
    AnnotationTextbox(UUID id, String createdBy, String lastModifiedBy, Date modifiedOn, Date createdOn, String className, String type, long page, UUID uuid, String colour, long width, List<Point> lines, List<Rectangle> rectangles, long x, long y, long size, long height) {
        super(id, createdBy, lastModifiedBy, modifiedOn, createdOn, className, type, page, uuid, colour, width, lines, rectangles, x, y, size, height);
    }

//    private String className = "Annotation";
//    private String type = "textbox";
//    private long page;
//    private UUID uuid;
//    private String colour;
//    private long size;
//    private long width;
//    private long height;
//    private long x;
//    private long y;




//    Textbox
//    {
//        "class": "Annotation",
//        "type": "textbox",
//        "page": 1,
//        "uuid": "efd4ded2-c5cb-4064-8fe8-4217a0565e97",
//        "content": "Hello World!",
//        "color": "000000",
//        "size": 24,
//        "width": 259,
//        "height": 36,
//        "x": 126,
//        "y": 82
//    }

}
