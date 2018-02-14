package uk.gov.hmcts.reform.em.annotation.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.repository.AnnotationRepository;

import java.util.UUID;

@Service
public class AnnotationService {

    private final AnnotationRepository repository;
    private final StoredAnnotationSetService annotationSetService;

    @Autowired
    public AnnotationService(AnnotationRepository repository,
                             StoredAnnotationSetService annotationSetService) {
        this.repository = repository;
        this.annotationSetService = annotationSetService;
    }

    public Annotation getById(UUID annotationId) {
        return repository.findOne(annotationId);
    }

    public Annotation save(UUID annotationSetId, Annotation annotation) {
        AnnotationSet annotationSet = annotationSetService.getAnnotationSet(annotationSetId);
        annotation.setAnnotationSet(annotationSet);
        return repository.save(annotation);
    }

    public void delete(UUID annotationId) {
        repository.delete(annotationId);
    }

    public Annotation update(UUID uuid, Annotation annotation) {
        Annotation originalAnnotation = getById(uuid);
        BeanUtils.copyProperties(annotation, originalAnnotation, "annotationSet", "uuid");
        repository.save(originalAnnotation);
        return originalAnnotation;
    }
}
