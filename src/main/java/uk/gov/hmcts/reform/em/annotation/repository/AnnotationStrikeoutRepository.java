package uk.gov.hmcts.reform.em.annotation.repository;


import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationHighlight;

@Repository
public interface AnnotationStrikeoutRepository extends AnnotationBaseRepository<AnnotationHighlight> {

}
