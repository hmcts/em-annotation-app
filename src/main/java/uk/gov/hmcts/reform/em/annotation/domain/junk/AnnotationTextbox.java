package uk.gov.hmcts.reform.em.annotation.domain.junk;

import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class AnnotationTextbox {

    private String colour;
    private long size;
    private long width;
    private long height;
    private long x;
    private long y;


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
