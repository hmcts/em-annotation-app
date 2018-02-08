package uk.gov.hmcts.reform.em.annotation.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;

import java.util.UUID;

@Repository
public interface AnnotationSetRepository extends PagingAndSortingRepository<AnnotationSet, UUID> {

    @Query("select s from AnnotationSet s where s.documentUri = :#{#url}")
    Page<AnnotationSet> searchByUrlDocumentUrl(@NonNull @Param("url")String url, @NonNull Pageable pageable);

}
