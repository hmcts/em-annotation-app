package uk.gov.hmcts.reform.em.annotation.hateos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.core.Relation;
import uk.gov.hmcts.reform.em.annotation.controllers.AnnotationController;
import uk.gov.hmcts.reform.em.annotation.controllers.StoredAnnotationSetController;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "annotationSets")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnnotationSetHalResource extends HalResource {

    private UUID uuid;

    private String createdBy;

    private String lastModifiedBy;

    private Date modifiedOn;

    private Date createdOn;

    private String documentUri;

    private Set<AnnotationHalResource> annotations = new HashSet<>();


    public AnnotationSetHalResource(@NonNull AnnotationSet annotationSet) {
        BeanUtils.copyProperties(annotationSet, this, "annotations");
        if (annotationSet.getAnnotations() != null && !annotationSet.getAnnotations().isEmpty()) {
            this.annotations = annotationSet.getAnnotations().stream().map(AnnotationHalResource::new).collect(Collectors.toSet());
        }
        add(linkTo(methodOn(StoredAnnotationSetController.class).retrieveAnnotationSet(annotationSet.getUuid())).withSelfRel());
        add(linkTo(methodOn(AnnotationController.class).createAnnotation(annotationSet.getUuid(), null)).withRel("add-annotation"));
    }


}
