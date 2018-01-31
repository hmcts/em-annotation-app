package uk.gov.hmcts.reform.em.annotation.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Annotation {

    @Getter
    @Setter
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID uuid;

    @Getter
    @Setter
    @CreatedBy
    private String createdBy;

    @Getter
    @Setter
    @LastModifiedBy
    private String lastModifiedBy;

    @Getter
    @Setter
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedOn;

    @Getter
    @Setter
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Getter
    @Setter
    @ManyToOne
    private AnnotationSet annotationSet;

    @Getter
    @Setter
    @ValidateType
    private AnnotationType type; // "drawing" / "highlight" / "point" / "strikeout" / "textbox" / "page{Custom}"

    @Getter
    @Setter
    @NotNull
    private long page;

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    private Set<Comment> comments;

    @Getter
    @Setter
    private String colour; //Drawing, Highlight, Strikeout, Textbox

    @Getter
    @Setter
    private Long x; // Point, Textbox

    @Getter
    @Setter
    private Long y; // Point, Textbox

    @Getter
    @Setter
    private Long fontSize; // (size) Textbox

    @Getter
    @Setter
    private Long height; // Textbox

    @Getter
    @Setter
    private Long width; // Drawing, Textbox

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    @OrderColumn(name = "itm_idx")
    private List<Point> lines; // Drawing

    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    private Set<Rectangle> rectangles;// Highlight, Strikeout


    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String s = "";

        try {
            s = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return s;
    }
// Validator

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
