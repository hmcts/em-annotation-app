package uk.gov.hmcts.reform.em.annotation.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.service.StoredAnnotationSetService;

import java.util.UUID;

@RestController
@RequestMapping(
    path = "/annotationSets")
@Api("Endpoint for Storing Annotation")
public class StoredAnnotationSetController {

    @Autowired
    private StoredAnnotationSetService storedAnnotationSetService;

    @PostMapping(value = "")
    @ApiOperation("Create Annotation Set.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Object.class)
    })
    public ResponseEntity<AnnotationSet> createAnnotationSet(AnnotationSet annotationSet1) {

        AnnotationSet annotationSet = storedAnnotationSetService.createAnnotationSet(annotationSet1);

        return ResponseEntity.ok().body(annotationSet);
    }

    @GetMapping(value = "{id}")
    @ApiOperation("Retrieve Annotation Set instance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Object.class)
    })
    public ResponseEntity<Object> retrieveAnnotationSet(@PathVariable UUID id) {
        return ResponseEntity.ok().body(new Object());
    }

    @PutMapping(value = "{id}")
    @ApiOperation("Update Annotation Set instance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Object.class)
    })
    public ResponseEntity<Object> updateAnnotationSet(@PathVariable UUID id, AnnotationSet annotationSet) {
        return ResponseEntity.ok().body(new Object());
    }

    @DeleteMapping(value = "{id}")
    @ApiOperation("Delete Annotation Set instance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Object.class)
    })
    public ResponseEntity<Object> deleteAnnotationSet(@PathVariable UUID id) {
        return ResponseEntity.ok().body(new Object());
    }



}
