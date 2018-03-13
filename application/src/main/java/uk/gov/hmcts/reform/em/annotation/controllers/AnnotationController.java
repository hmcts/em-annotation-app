package uk.gov.hmcts.reform.em.annotation.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.hateos.AnnotationHalResource;
import uk.gov.hmcts.reform.em.annotation.service.AnnotationService;

import java.util.UUID;

@RestController
@RequestMapping("annotation-sets/{annotationSetId}/annotations")
@Api("End point for manipulating annotations")
public class AnnotationController {

    private final AnnotationService annotationService;

    @Autowired
    public AnnotationController(AnnotationService annotationService) {
        this.annotationService = annotationService;
    }

    @GetMapping("{annotationId}")
    public ResponseEntity<AnnotationHalResource> getAnnotation(@PathVariable UUID annotationSetId,
                                               @PathVariable UUID annotationId) {
        Annotation annotation = annotationService.getById(annotationId);
        if (annotation != null) {
            return ResponseEntity.ok(new AnnotationHalResource(annotation));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{annotationId}")
    public ResponseEntity<AnnotationHalResource> updateAnnotation(@PathVariable UUID annotationId,
                                                                  @RequestBody Annotation annotation) {
        return ResponseEntity.ok(new AnnotationHalResource(annotationService.update(annotationId, annotation)));
    }

    @DeleteMapping("{annotationId}")
    public ResponseEntity<AnnotationHalResource> deleteAnnotation(@PathVariable UUID annotationId) {
        annotationService.delete(annotationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    public ResponseEntity<AnnotationHalResource> createAnnotation(@PathVariable UUID annotationSetId,
                                                                  @RequestBody Annotation annotation) {
        AnnotationHalResource annotationHalResource =
            new AnnotationHalResource(annotationService.save(annotationSetId, annotation));
        return ResponseEntity.created(annotationHalResource.getUri()).body(annotationHalResource);
    }





}
