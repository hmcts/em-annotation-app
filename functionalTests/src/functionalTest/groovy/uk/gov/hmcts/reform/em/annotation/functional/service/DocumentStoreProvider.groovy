package uk.gov.hmcts.reform.em.annotation.functional.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import uk.gov.hmcts.reform.em.annotation.functional.AuthTokenProvider
import uk.gov.hmcts.reform.em.annotation.functional.utilities.FileUtils
import uk.gov.hmcts.reform.em.annotation.functional.utilities.V1MediaTypes

import static io.restassured.RestAssured.given

@Service
class DocumentStoreProvider {

    private final String dmStoreAppBaseUri
    private final AuthTokenProvider authTokenProvider

    FileUtils fileUtils = new FileUtils()

    final String FILES_FOLDER = 'files/'

    final String ATTACHMENT_4_PDF = '1MB.PDF'

    @Autowired
    DocumentStoreProvider(
        AuthTokenProvider authTokenProvider,
        @Value('${base-urls.dm-store-app}') String dmStoreAppBaseUri
    ) {
        this.dmStoreAppBaseUri = dmStoreAppBaseUri
        this.authTokenProvider = authTokenProvider
        System.out.println("DM STORE APP URL - " + dmStoreAppBaseUri)
    }

    def createDocumentAndGetUrlAs(username, filename = null, classification = null, roles = null, metadata = null) {
        createDocument(username, filename, classification, roles, metadata)
            .path("_embedded.documents[0]._links.self.href")
    }

    def createDocument(username,  filename = null, classification = null, roles = null, metadata = null) {
        def request = givenDMRequest(username)
            .multiPart("files", file( filename ?: ATTACHMENT_4_PDF), MediaType.APPLICATION_PDF_VALUE)
            .multiPart("classification", classification ?: "PUBLIC")

        roles?.each { role ->
            request.multiPart("roles", role)
        }

        if (metadata) {
            request.accept(V1MediaTypes.V1_HAL_DOCUMENT_AND_METADATA_COLLECTION_MEDIA_TYPE_VALUE)
            metadata?.each { key, value ->
                request.multiPart("metadata[${key}]", value)
            }
        }

        request
            .expect()
            .statusCode(200)
            .when()
            .post("/documents")
    }

    def givenDMRequest(username = null, userRoles = null) {
        def request =  given().baseUri(dmStoreAppBaseUri)
        if (username) {
            request = request.header("serviceauthorization", serviceToken())
            if (username) {
                request = request.header("user-id", username)
            }
            if (userRoles) {
                request = request.header("user-roles", userRoles.join(','))
            }
        }
        request
    }


    def file(fileName) {
        fileUtils.getResourceFile(FILES_FOLDER + fileName)
    }

    def serviceToken() {
        authTokenProvider.findServiceToken()
    }
}
