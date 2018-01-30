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
import uk.gov.hmcts.reform.em.annotation.service.StoredAnnotationSetService;

import java.util.UUID;

@RestController
@RequestMapping(
    path = "/annotationSets")
@Api("Endpoint for Storing Annotation")
public class AnnotationSetSearchController {

    @Autowired
    private StoredAnnotationSetService storedAnnotationSetService;

    @GetMapping(value = "/findAllByDocumentUrl")
    @ApiOperation("Retrieve all Annotation Sets associated with a given DM URL")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Object.class)
    })
    public ResponseEntity<Object> findAllAnnotationSetByDocumentUrl() {
        return ResponseEntity.status(HttpStatus.OK).body(ImmutableMap.of("message","Hello World"));
    }


    @GetMapping(value = "/filter")
    @ApiOperation("Retrieve Annotation Set with filter options")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = Object.class)
    })
    public ResponseEntity<Object> filterAnnotationSet() {
        return ResponseEntity.ok().body(new Object());
    }

}
