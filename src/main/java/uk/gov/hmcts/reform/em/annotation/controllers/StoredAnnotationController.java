package uk.gov.hmcts.reform.em.annotation.controllers;

import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    path = "/annotationSets")
@Api("Endpoint for Storing Annotation")
public class StoredAnnotationController {

    @GetMapping()
    @ApiOperation("")
    public ResponseEntity<Object> helloWorld() {
        return ResponseEntity.status(HttpStatus.OK).body(ImmutableMap.of("message","Hello World"));
    }

}
