package uk.gov.hmcts.reform.em.annotation.controllers;

import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.em.annotation.service.StoredAnnotationSetSearchService;

import java.util.UUID;

@RestController
@RequestMapping(
    path = "/annotationSets")
@Api("Endpoint for Storing Annotation")
public class StoredAnnotationSetController {

    @Autowired
    private StoredAnnotationSetSearchService storedAnnotationSetService;

    @GetMapping()
    @ApiOperation("")
    public ResponseEntity<Object> helloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body(ImmutableMap.of("message","Hello World"));
    }

    @PostMapping(value = "")
    @ApiOperation("Create Annotation Set.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Object.class)
    })
    public ResponseEntity<Object> createAnnotationSet() {
        return ResponseEntity.ok().body(new Object());
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
    public ResponseEntity<Object> updateAnnotationSet(@PathVariable UUID id) {
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
