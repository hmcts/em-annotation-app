package uk.gov.hmcts.reform.em.annotation.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.util.UUID;


@NoRepositoryBean
public interface AnnotationBaseRepository<T extends Annotation> extends CrudRepository<T, UUID> {

}
