package uk.gov.hmcts.reform.em.annotation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
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
    private Long pointX; // Point, Textbox

    @Getter
    @Setter
    private Long pointY; // Point, Textbox

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


//    public String toString() {
//        ObjectMapper mapper = new ObjectMapper();
//        String s = "";
//
//        try {
//            s = mapper.writeValueAsString(this);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return s;
//    }
}
