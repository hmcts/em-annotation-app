package uk.gov.hmcts.reform.em.annotation.hateos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.core.Relation;
import uk.gov.hmcts.reform.em.annotation.controllers.StoredAnnotationSetController;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "annotationset")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnnotationSetHalResource extends HalResource {

    private UUID uuid;

    private String createdBy;

    private String lastModifiedBy;

    private Date modifiedOn;

    private Date createdOn;

    private String documentUri;

    private Set<Annotation> annotations;


    public AnnotationSetHalResource(@NonNull AnnotationSet annotationSet) {
        BeanUtils.copyProperties(annotationSet, this);
        add(linkTo(methodOn(StoredAnnotationSetController.class).retrieveAnnotationSet(annotationSet.getUuid())).withSelfRel());
//        add(linkTo(methodOn(StoredAnnotationSetController.class).retrieveAnnotationSet(annotationSet.getUuid())).withRel("document"));
    }


}
