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
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EntityListeners(AuditingEntityListener.class)
public class Annotation {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    @CreatedBy
    private String createdBy;

    @Getter
    @Setter
    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedOn;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;


//    MODEL Stuff

    private String className = "Annotation";
    private String type; // "drawing" / "highlight" / "point" / "strikeout" / "textbox"
    private long page;
    private UUID uuid;

    private String colour; //Drawing, Highlight, Strikeout, Textbox

    private long width; // Drawing, Textbox
    private List<Point> lines; // Drawing

    private List<Rectangle> rectangles;// Highlight, Strikeout

    private long x; // Point, Textbox
    private long y; // Point, Textbox

    private long size; // Textbox
    private long height; // Textbox


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
