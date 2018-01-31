package uk.gov.hmcts.reform.em.annotation.domain.junk;

import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uk.gov.hmcts.reform.em.annotation.domain.Rectangle;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.List;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnnotationHighlight {

    private String colour;
    private List<Rectangle> rectangles;




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
