package no.stackcanary.javaspringoauth2client;

import no.stackcanary.javaspringoauth2client.resource.dto.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.util.WiremockStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FullIntegrationTest extends IntegrationTestBase {

    @Test
    @DisplayName("Full happy path for GET /api/v1/employee/{id}")
    void scenario1() {
        WiremockStubs.stubGetEmployeeResourceServerOKResponse();
        testClient.get().uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employee/{id}")
                        .build(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeResponse.class)
                .value(response -> {
                    assert response != null;
                    assert response.employee() != null;
                    assert response.employee().id() == 1;
                    assert response.employee().certifications() != null;
                    assert response.employee().certifications().size() == 2;
                    assert response.employee().employer() != null;
                    assert response.employee().employer().name().equals("Big International Firm");

                });
    }
}
