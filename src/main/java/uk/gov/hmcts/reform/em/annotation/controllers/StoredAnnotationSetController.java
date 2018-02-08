package uk.gov.hmcts.reform.em.annotation.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.hateos.AnnotationSetHalResource;
import uk.gov.hmcts.reform.em.annotation.service.StoredAnnotationSetService;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@Controller
@RequestMapping(
    path = "/annotation-sets")
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
    public ResponseEntity<AnnotationSetHalResource> createAnnotationSet(@RequestBody @Valid AnnotationSet body) throws URISyntaxException {

        AnnotationSetHalResource annotationSetHalResource = new AnnotationSetHalResource(storedAnnotationSetService.createAnnotationSet(body));
        return ResponseEntity.created(annotationSetHalResource.getUri()).body(annotationSetHalResource);
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

    @PutMapping(value = "{uuid}")
    @ApiOperation("Update Annotation Set instance.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = AnnotationSet.class)
    })
    public ResponseEntity updateAnnotationSet(@PathVariable UUID uuid, @RequestBody @Valid AnnotationSet body) {

//        storedAnnotationSetService.updateAnnotationSet(uuid,body);

        return ResponseEntity.status(NOT_IMPLEMENTED).build();
    }

    @DeleteMapping(value = "{uuid}")
    @ApiOperation("Delete Annotation Set instance.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content")
    })
    public ResponseEntity deleteAnnotationSet(@PathVariable UUID uuid) {
        storedAnnotationSetService.deleteAnnotationSet(uuid);
        return ResponseEntity.noContent().build();
    }

}
