package uk.gov.hmcts.reform.em.annotation.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;

import java.util.UUID;

public interface AnnotationRepository extends PagingAndSortingRepository<Annotation, UUID> {

}
