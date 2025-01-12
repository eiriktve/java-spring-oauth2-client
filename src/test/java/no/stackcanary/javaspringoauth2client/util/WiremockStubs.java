package no.stackcanary.javaspringoauth2client.util;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import no.stackcanary.javaspringoauth2client.resource.dto.response.IdResponse;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class WiremockStubs {

    public static void stubOauthIssuerTokenResponse() {
        stubFor(post(urlPathMatching("/oauth2/token"))
                .willReturn(aResponse()
                        .withBody("""
                                {
                                    "access_token": "very.secret.token",
                                    "scope": "employeeResponse.read employeeResponse.create employeeResponse.edit employeeResponse.delete",
                                    "token_type": "Bearer",
                                    "expires_in": 1799
                                }
                                """)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())));
    }

    public static void stubPostEmployeeResourceServerOKResponse() {
        stubFor(post(urlPathMatching("/employee"))
                .willReturn(aResponse()
                        .withBodyFile("resource-server-responses/post_employee_OK_response.json")
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.CREATED.value())));
    }

    public static void stubGetEmployeeResourceServerOKResponse() {
        stubFor(get(urlPathMatching("/employee/[0-9]"))
                .willReturn(aResponse()
                        .withBodyFile("resource-server-responses/get_employee_OK_response.json")
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())));
    }

    public static void stubPutEmployeeResourceServerOKResponse() {
        stubFor(put(urlPathMatching("/employee/[0-9]"))
                .willReturn(aResponse()
                        .withBodyFile("resource-server-responses/put_employee_OK_response.json")
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())));
    }

    public static void stubDeleteEmployeeResourceServerOKResponse() {
        stubFor(delete(urlPathMatching("/employee/[0-9]"))
                .willReturn(aResponse()
                        // Just to show that you can also build your mock response directly
                        .withResponseBody(ResponseDefinitionBuilder.jsonResponse(IdResponse.builder()
                                        .id("1")
                                        .message("Employee deleted")
                                        .build())
                                .getReponseBody())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())));
    }

    public static void stubDeleteEmployeeResourceServerNotFoundResponse() {
        stubFor(delete(urlPathMatching("/employee/[0-9]"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value())));
    }
}
