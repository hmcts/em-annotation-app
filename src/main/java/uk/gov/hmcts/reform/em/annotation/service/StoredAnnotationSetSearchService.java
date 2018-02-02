package uk.gov.hmcts.reform.em.annotation.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.repository.AnnotationSetRepository;

@Service
public class StoredAnnotationSetSearchService {

    private AnnotationSetRepository annotationSetRepository;

    @Autowired
    public StoredAnnotationSetSearchService(AnnotationSetRepository annotationSetRepository) {
        this.annotationSetRepository = annotationSetRepository;
    }

    public Page<AnnotationSet> searchByUrlDocumentUrl(@NonNull String documentUri, @NonNull Pageable pageable) {
        return annotationSetRepository.searchByUrlDocumentUrl(documentUri,pageable);
    }


}
