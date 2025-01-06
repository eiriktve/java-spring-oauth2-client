package no.stackcanary.javaspringoauth2client.util;

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
                                    "scope": "employee.read employee.create employee.edit employee.delete",
                                    "token_type": "Bearer",
                                    "expires_in": 1799
                                }
                                """)
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())));
    }

    public static void stubGetEmployeeResourceServerOKResponse() {
        stubFor(get(urlPathMatching("/employee/[0-9]+"))
                .willReturn(aResponse()
                        .withBodyFile("resource-api-responses/get_employee_OK_response.json")
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.OK.value())));
    }
}
