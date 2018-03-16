package uk.gov.hmcts.reform.em.annotation.service;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.gov.hmcts.reform.em.annotation.domain.Annotation;
import uk.gov.hmcts.reform.em.annotation.domain.Comment;
import uk.gov.hmcts.reform.em.annotation.repository.AnnotationRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnnotationServiceTest {

    public static final UUID ORIGINAL_UUID = UUID.randomUUID();

    @Mock
    private AnnotationRepository repository;
    @Mock
    private StoredAnnotationSetService annotationSetService;

    private AnnotationService service;

    @Before
    public void setUp() throws Exception {
        service = new AnnotationService(repository, annotationSetService);
    }

    @Test
    public void should_update_an_annotation_without_comments() {
        Annotation originalAnnotation = Annotation.builder()
            .height(100L)
            .build();
        Annotation newAnnotation = Annotation.builder()
            .height(200L)
            .build();

        when(repository.findOne(ORIGINAL_UUID)).thenReturn(originalAnnotation);

        Annotation updatedAnnotation = service.update(ORIGINAL_UUID, newAnnotation);

        assertThat(updatedAnnotation.getHeight(), equalTo(newAnnotation.getHeight()));
    }

    @Test
    public void should_update_an_annotation_and_keep_createdBy_and_createdOn() {
        Date createdOn = new Date();
        String user = "1";
        Annotation originalAnnotation = Annotation.builder()
            .height(100L)
            .createdBy(user)
            .createdOn(createdOn)
            .build();
        Annotation newAnnotation = Annotation.builder()
            .height(200L)
            .build();

        when(repository.findOne(ORIGINAL_UUID)).thenReturn(originalAnnotation);

        Annotation updatedAnnotation = service.update(ORIGINAL_UUID, newAnnotation);

        assertThat(updatedAnnotation.getCreatedBy(), equalTo(user));
        assertThat(updatedAnnotation.getCreatedOn(), equalTo(createdOn));
    }

    @Test
    public void should_update_an_annotation_with_comment_without_comment() {
        Annotation originalAnnotation = Annotation.builder()
            .comments(new HashSet<>(ImmutableSet.of(Comment.builder().content("New comment").build())))
            .build();
        Annotation newAnnotation = Annotation.builder()
            .build();

        when(repository.findOne(ORIGINAL_UUID)).thenReturn(originalAnnotation);

        Annotation updatedAnnotation = service.update(ORIGINAL_UUID, newAnnotation);

        assertThat(updatedAnnotation.getComments().isEmpty(), equalTo(true));
    }

    @Test
    public void should_update_an_annotation_without_comment_with_comment() {
        Annotation originalAnnotation = Annotation.builder()
            .build();
        Annotation newAnnotation = Annotation.builder()
            .comments(new HashSet<>(ImmutableSet.of(Comment.builder().content("New comment").build())))
            .build();

        when(repository.findOne(ORIGINAL_UUID)).thenReturn(originalAnnotation);

        Annotation updatedAnnotation = service.update(ORIGINAL_UUID, newAnnotation);

        Comment updatedComment = updatedAnnotation.getComments().iterator().next();
        assertThat(updatedComment.getContent(), equalTo(newAnnotation.getComments().iterator().next().getContent()));
    }

    @Test
    public void should_update_an_annotation_with_comment_with_comment() {
        Annotation originalAnnotation = Annotation.builder()
            .comments(new HashSet<>(ImmutableSet.of(Comment.builder().content("Old comment").build())))
            .build();
        Annotation newAnnotation = Annotation.builder()
            .comments(new HashSet<>(ImmutableSet.of(Comment.builder().content("New comment").build())))
            .build();

        when(repository.findOne(ORIGINAL_UUID)).thenReturn(originalAnnotation);

        Annotation updatedAnnotation = service.update(ORIGINAL_UUID, newAnnotation);

        Comment updatedComment = updatedAnnotation.getComments().iterator().next();
        assertThat(updatedComment.getContent(), equalTo(newAnnotation.getComments().iterator().next().getContent()));
        assertThat(updatedComment.getAnnotation(), equalTo(originalAnnotation));
    }
}
