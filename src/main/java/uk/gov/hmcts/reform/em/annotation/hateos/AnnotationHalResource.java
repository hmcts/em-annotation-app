package uk.gov.hmcts.reform.em.annotation.hateos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import uk.gov.hmcts.reform.em.annotation.controllers.AnnotationController;
import uk.gov.hmcts.reform.em.annotation.controllers.StoredAnnotationSetController;
import uk.gov.hmcts.reform.em.annotation.domain.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnnotationHalResource extends HalResource {

    private String createdBy;

    private String lastModifiedBy;

    private Date modifiedOn;

    private Date createdOn;

    private AnnotationType type; // "drawing" / "highlight" / "point" / "strikeout" / "textbox" / "page{Custom}"

    private long page;

    private Set<Comment> comments;

    private String colour; //Drawing, Highlight, Strikeout, Textbox

    private Long pointX; // Point, Textbox

    private Long pointY; // Point, Textbox

    private Long fontSize; // (size) Textbox

    private Long height; // Textbox

    private Long width; // Drawing, Textbox

    private List<Point> lines; // Drawing

    private Set<Rectangle> rectangles;// Highlight, Strikeout

    public AnnotationHalResource(Annotation annotation) {
        BeanUtils.copyProperties(annotation, this);
        add(linkTo(methodOn(AnnotationController.class).getAnnotation(annotation.getAnnotationSet().getUuid(), annotation.getUuid())).withSelfRel());
        add(linkTo(methodOn(StoredAnnotationSetController.class).retrieveAnnotationSet(annotation.getAnnotationSet().getUuid())).withRel("annotation-set"));
    }
}
