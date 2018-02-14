package uk.gov.hmcts.reform.em.annotation.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class AnnotationSetTest {

    @Test
    public void should_serialise_uuid() throws JsonProcessingException {
        AnnotationSet annotationSet = new AnnotationSet();
        annotationSet.setUuid(UUID.randomUUID());

        String asString = new ObjectMapper().writer().writeValueAsString(annotationSet);

        assertThat(asString, containsString(annotationSet.getUuid().toString()));
    }
}
