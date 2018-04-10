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
import java.util.*;

@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Annotation {

    public Annotation() {
    }

    public Annotation(UUID uuid, String createdBy,
                      String lastModifiedBy,
                      Date modifiedOn,
                      Date createdOn,
                      AnnotationSet annotationSet,
                      AnnotationType type,
                      long page,
                      Set<Comment> comments,
                      String colour,
                      Long pointX,
                      Long pointY,
                      Long fontSize,
                      Long height,
                      Long width,
                      List<Point> lines,
                      Set<Rectangle> rectangles) {
        this.uuid = uuid;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.modifiedOn = modifiedOn;
        this.createdOn = createdOn;
        this.annotationSet = annotationSet;
        this.type = type;
        this.page = page;
        this.colour = colour;
        this.pointX = pointX;
        this.pointY = pointY;
        this.fontSize = fontSize;
        this.height = height;
        this.width = width;
        this.lines = lines;
        this.rectangles = rectangles;
        setComments(comments);
    }

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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "annotation")
    private Set<Comment> comments = new HashSet<>();

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
        if (this.comments != null) {
            this.comments.forEach(comment -> comment.setAnnotation(this));
        }
    }

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    @OrderColumn(name = "itm_idx")
    private List<Point> lines; // Drawing

    public final void setLines(List<Point> lines) {
        if (this.lines != null) {
            this.lines.clear();
            this.lines.addAll(lines);
        } else {
            this.lines = lines;
        }
        if (this.lines != null) {
            this.lines.forEach(line -> line.setAnnotation(this));
        }
    }

    @Getter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "annotation")
    private Set<Rectangle> rectangles;// Highlight, Strikeout

    public final void setRectangles(Set<Rectangle> rectangles) {
        if (this.rectangles != null) {
            this.rectangles.clear();
            this.rectangles.addAll(rectangles);
        } else {
            this.rectangles = rectangles;
        }
        if (this.rectangles != null) {
            this.rectangles.forEach(rectangle -> rectangle.setAnnotation(this));
        }
    }

    public void setColor(String color) {
        setColour(color);
    }

    public void update(Annotation newAnnotation) {
        this.comments.clear();
        this.comments.addAll(newAnnotation.comments);
    }

    public boolean isNew() {
        return uuid == null;
    }

    public void addComment(Comment comment) {
        comment.setAnnotation(this);
        getComments().add(comment);
    }
}
