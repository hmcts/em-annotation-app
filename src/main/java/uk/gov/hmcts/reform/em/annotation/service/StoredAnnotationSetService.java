package uk.gov.hmcts.reform.em.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.repository.AnnotationSetRepository;

import java.util.UUID;

@Service
public class StoredAnnotationSetService {

    private AnnotationSetRepository annotationSetRepository;

    @Autowired
    public StoredAnnotationSetService(AnnotationSetRepository annotationSetRepository) {
        this.annotationSetRepository = annotationSetRepository;
    }

    public AnnotationSet getAnnotationSet(UUID uuid) {
        return annotationSetRepository.findOne(uuid);
    }

    public AnnotationSet createAnnotationSet(AnnotationSet annotationSet) {
        return annotationSetRepository.save(annotationSet);
    }

    public void deleteAnnotationSet(UUID uuid) {
        annotationSetRepository.delete(uuid);
    }

    public void updateAnnotationSet(UUID uuid, AnnotationSet annotationSet) {

        if (annotationSetRepository.exists(uuid)) {
            AnnotationSet orgAnnotationSet = annotationSetRepository.findOne(uuid);
            orgAnnotationSet.getAnnotations().clear();
            orgAnnotationSet.getAnnotations().addAll(annotationSet.getAnnotations());

            annotationSetRepository.save(orgAnnotationSet);
        } else {
            throw new RuntimeException("No Annotation set found");
        }
    }

}
