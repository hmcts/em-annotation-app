package uk.gov.hmcts.reform.em.annotation.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.em.annotation.domain.AnnotationSet;
import uk.gov.hmcts.reform.em.annotation.service.StoredAnnotationSetSearchService;

@RestController
@RequestMapping(
    path = "/annotation-sets")
@Api("Endpoint for Storing Annotation")
public class AnnotationSetSearchController {

    private StoredAnnotationSetSearchService storedAnnotationSetSearchService;

    @Autowired
    public AnnotationSetSearchController(StoredAnnotationSetSearchService storedAnnotationSetSearchService) {
        this.storedAnnotationSetSearchService = storedAnnotationSetSearchService;
    }

    @GetMapping(value = "/find-all-by-document-url")
    @ApiOperation("Retrieve all Annotation Sets associated with a given DM URL")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = PagedResources.class)
    })
    public ResponseEntity<PagedResources<Resource<AnnotationSet>>> findAllAnnotationSetByDocumentUrl(
        @RequestParam("url") String url,
        Pageable pageable,
        PagedResourcesAssembler<AnnotationSet> assembler) {

        Page<AnnotationSet> page = storedAnnotationSetSearchService.searchByUrlDocumentUrl(url, pageable);

        return ResponseEntity.ok().body(assembler.toResource(page));
    }


//    @GetMapping(value = "/filter")
//    @ApiOperation("Retrieve Annotation Set with filter options")
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Success", response = Object.class)
//    })
//    public ResponseEntity<Object> filterAnnotationSet() {
//        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new Object());
//    }

}
