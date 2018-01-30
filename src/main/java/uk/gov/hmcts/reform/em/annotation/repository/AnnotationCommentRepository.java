package uk.gov.hmcts.reform.em.annotation.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.util.UUID;

@Repository
public interface AnnotationCommentRepository extends PagingAndSortingRepository<AnnotationSet, UUID> {

}
