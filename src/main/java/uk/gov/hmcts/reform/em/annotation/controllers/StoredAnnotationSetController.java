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

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping(
    path = "/annotationSets")
@Api("Endpoint for Storing Annotation")
public class StoredAnnotationSetController {

    private StoredAnnotationSetService storedAnnotationSetService;

    @Autowired
    public StoredAnnotationSetController(StoredAnnotationSetService storedAnnotationSetService) {
        this.storedAnnotationSetService = storedAnnotationSetService;
    }

    @PostMapping(value = "")
    @ApiOperation("Create Annotation Set.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = AnnotationSet.class)
    })
    public ResponseEntity<AnnotationSet> createAnnotationSet(@RequestBody @Valid AnnotationSet body) throws URISyntaxException {

        AnnotationSet annotationSet = storedAnnotationSetService.createAnnotationSet(body);

        return ResponseEntity.ok(annotationSet);
    }

    @GetMapping(value = "{uuid}")
    @ApiOperation("Retrieve Annotation Set instance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = AnnotationSet.class)
    })
    public ResponseEntity<AnnotationSet> retrieveAnnotationSet(@PathVariable UUID uuid) {

        AnnotationSet annotationSet = storedAnnotationSetService.getAnnotationSet(uuid);

        if (annotationSet != null) {
            return ResponseEntity.ok().body(annotationSet);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

//    @PutMapping(value = "{uuid}")
//    @ApiOperation("Update Annotation Set instance.")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Success", response = AnnotationSet.class)
//    })
//    public ResponseEntity updateAnnotationSet(@PathVariable UUID uuid, @RequestBody @Valid AnnotationSet body) {
//
//        storedAnnotationSetService.updateAnnotationSet(uuid,body);
//
//        return ResponseEntity.ok().build();
//    }

//    @DeleteMapping(value = "{uuid}")
//    @ApiOperation("Delete Annotation Set instance.")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Success", response = AnnotationSet.class)
//    })
//    public ResponseEntity<Object> deleteAnnotationSet(@PathVariable UUID uuid) {
//
//        storedAnnotationSetService.deleteAnnotationSet(uuid);
//
//        return ResponseEntity.ok().body(new Object());
//    }

}
