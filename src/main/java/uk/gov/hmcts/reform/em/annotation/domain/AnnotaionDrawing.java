package uk.gov.hmcts.reform.em.annotation.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnnotaionDrawing extends Annotation {

    private String colour;

    private long width;

    private List<Point> lines;


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
