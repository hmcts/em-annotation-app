package uk.gov.hmcts.reform.em.annotation.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.repository.AnnotationSetRepository;
import uk.gov.hmcts.reform.em.annotation.service.security.SecurityUtilService;

@Service
public class StoredAnnotationSetSearchService {

    private AnnotationSetRepository annotationSetRepository;
    private SecurityUtilService securityUtilService;

    @Autowired
    public StoredAnnotationSetSearchService(AnnotationSetRepository annotationSetRepository,
                                            SecurityUtilService securityUtilService) {
        this.annotationSetRepository = annotationSetRepository;
        this.securityUtilService = securityUtilService;
    }

    public Page<AnnotationSet> searchByUrlDocumentUrl(@NonNull String url, @NonNull Pageable pageable) {
        return annotationSetRepository.searchByUrlDocumentUrl(url, securityUtilService.getUserId(), pageable);
    }


}
