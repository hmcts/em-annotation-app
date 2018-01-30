package uk.gov.hmcts.reform.em.annotation.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationHighlight;

import java.util.UUID;

@Repository
public interface AnnotationHighlightRepository extends AnnotationBaseRepository<AnnotationHighlight> {

}
