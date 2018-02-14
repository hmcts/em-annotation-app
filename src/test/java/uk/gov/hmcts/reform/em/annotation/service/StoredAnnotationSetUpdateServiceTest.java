package uk.gov.hmcts.reform.em.annotation.service;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.auditing.AuditingHandler;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.domain.Comment;
import uk.gov.hmcts.reform.em.annotation.repository.AnnotationSetRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoredAnnotationSetUpdateServiceTest {

    @Mock
    private AnnotationSetRepository annotationSetRepository;
    @Mock
    private AuditingHandler auditingHandler;

    private StoredAnnotationSetUpdateService service;
    private AnnotationSet oldAnnotationSet;
    private AnnotationSet newAnnotationSet;
    private final UUID setUuid = UUID.randomUUID();
    private final UUID firstAnnotationUuid = UUID.randomUUID();

    @Before
    public void setUp() {
        service = new StoredAnnotationSetUpdateService(annotationSetRepository, auditingHandler);
    }

    @Test
    public void should_update_any_existing_annotations() {
        givenASetWithAnExistingAnnotation();
        andANewSetWithUpdatedAnnotation();

        service.updateAnnotationSet(setUuid, newAnnotationSet);

        thenOldAnnotationShouldBeUpdated();
    }

    @Test
    public void should_remove_any_annotations_that_do_not_exist() {
        givenASetWithAnExistingAnnotation();
        andANewSetWithAnnotations();

        service.updateAnnotationSet(setUuid, newAnnotationSet);

        thenOldAnnotationsShouldBeRemoved();
    }

    private void thenOldAnnotationsShouldBeRemoved() {
        ArgumentCaptor<AnnotationSet> argumentCaptor = ArgumentCaptor.forClass(AnnotationSet.class);
        verify(annotationSetRepository).save(argumentCaptor.capture());

        Annotation annotation = argumentCaptor.getValue().getAnnotations().iterator().next();
        Set<Comment> comments = annotation.getComments();
        assertThat(comments.isEmpty(), is(true));
    }

    private void thenOldAnnotationShouldBeUpdated() {
        ArgumentCaptor<AnnotationSet> argumentCaptor = ArgumentCaptor.forClass(AnnotationSet.class);
        verify(annotationSetRepository).save(argumentCaptor.capture());

        Annotation annotation = argumentCaptor.getValue().getAnnotations().iterator().next();
        Comment comment = annotation.getComments().iterator().next();
        assertThat(comment.getContent(), equalTo("New note"));
    }

    private void andANewSetWithUpdatedAnnotation() {
        newAnnotationSet = AnnotationSet.builder()
            .uuid(setUuid)
            .annotations(new HashSet<>(ImmutableSet.of(Annotation.builder()
                .uuid(firstAnnotationUuid)
                .comments(new HashSet<>(ImmutableSet.of(Comment.builder()
                    .content("New note")
                    .build())))
                .build())))
            .build();
    }

    private void andANewSetWithAnnotations() {
        newAnnotationSet = AnnotationSet.builder()
            .uuid(setUuid)
            .annotations(new HashSet<>(ImmutableSet.of(Annotation.builder()
                .uuid(firstAnnotationUuid)
                .comments(Collections.emptySet())
                .build())))
            .build();
    }

    private void givenASetWithAnExistingAnnotation() {
        oldAnnotationSet = AnnotationSet.builder()
            .uuid(setUuid)
            .annotations(new HashSet<>(ImmutableSet.of(Annotation.builder()
                .uuid(firstAnnotationUuid)
                .comments(new HashSet<>(ImmutableSet.of(Comment.builder()
                    .content("Old note")
                    .build())))
                .build())))
            .build();
        when(annotationSetRepository.findOne(setUuid)).thenReturn(oldAnnotationSet);
    }
}
