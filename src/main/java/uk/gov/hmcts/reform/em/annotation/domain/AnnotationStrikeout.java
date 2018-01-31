package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnnotationStrikeout extends Annotation {

    private String colour;
    private List<Rectangle> rectangles;


//    private String className = "Annotation";
//    private String type = "strikeout";
//    private long page;
//    private UUID uuid;
//    private String colour;
//    private List<Rectangle> rectangles;

    //    Strikeout
//    {
//        "class": "Annotation",
//        "type": "strikeout"
//        "page": 1,
//        "uuid": "ad9fe5b8-699d-4711-a94e-4b0eb02e551f",
//        "color": "FF0000",
//        "rectangles": [
//        {
//            "height": 12,
//            "width": 457,
//            "x": 72,
//            "y": 147
//        },
//        {
//            "height": 12,
//            "width": 427,
//            "x": 72,
//            "y": 163
//        }
//  ]
//    }
}
