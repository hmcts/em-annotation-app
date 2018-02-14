package uk.gov.hmcts.reform.em.annotation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.exception.AnnotationSetNotFoundException;
import uk.gov.hmcts.reform.em.annotation.repository.AnnotationSetRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StoredAnnotationSetUpdateService {

    private AnnotationSetRepository annotationSetRepository;

    private AuditingHandler auditingHandler;

    @Autowired
    public StoredAnnotationSetUpdateService(AnnotationSetRepository annotationSetRepository,
                                            AuditingHandler auditingHandler) {
        this.annotationSetRepository = annotationSetRepository;
        this.auditingHandler = auditingHandler;
    }

    public void updateAnnotationSet(UUID uuid, AnnotationSet annotationSet) {
        AnnotationSet orgAnnotationSet = annotationSetRepository.findOne(uuid);
        if (orgAnnotationSet != null) {
            updateAnnotationSet(annotationSet, orgAnnotationSet);
        } else {
            throw new AnnotationSetNotFoundException("No AnnotationSet found");
        }
    }

    private void updateAnnotationSet(AnnotationSet newAnnotationSet, AnnotationSet orgAnnotationSet) {
        if (orgAnnotationSet.getAnnotations() == null) {
            orgAnnotationSet.setAnnotations(new HashSet<>());
        }
        matchOrDeleteExisting(newAnnotationSet, orgAnnotationSet);
        addNewAnnotations(newAnnotationSet, orgAnnotationSet);

        auditingHandler.markModified(orgAnnotationSet);
        annotationSetRepository.save(orgAnnotationSet);
    }

    private void matchOrDeleteExisting(AnnotationSet newAnnotationSet, AnnotationSet orgAnnotationSet) {
        Iterator<Annotation> iterator = orgAnnotationSet.getAnnotations().iterator();
        while (iterator.hasNext()) {
            Annotation annotation = iterator.next();
            Optional<Annotation> matchingNewAnnotation = newAnnotationSet.findMatching(annotation);
            if (matchingNewAnnotation.isPresent()) {
                annotation.update(matchingNewAnnotation.get());
                auditingHandler.markModified(annotation);
            } else {
                iterator.remove();
            }
        }
    }

    private void addNewAnnotations(AnnotationSet newAnnotationSet, AnnotationSet orgAnnotationSet) {
        Set<Annotation> newAnnotations = newAnnotationSet.getAnnotations()
            .stream().filter(a -> a.isNew())
            .collect(Collectors.toSet());
        orgAnnotationSet.getAnnotations().addAll(newAnnotations);
    }
}
