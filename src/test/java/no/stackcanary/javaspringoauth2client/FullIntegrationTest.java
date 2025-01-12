package no.stackcanary.javaspringoauth2client;

import no.stackcanary.javaspringoauth2client.resource.dto.request.CertificationRequest;
import no.stackcanary.javaspringoauth2client.resource.dto.request.EmployeeRequest;
import no.stackcanary.javaspringoauth2client.resource.dto.response.EmployeeResponse;
import no.stackcanary.javaspringoauth2client.resource.dto.response.IdResponse;
import no.stackcanary.javaspringoauth2client.util.WiremockStubs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;

class FullIntegrationTest extends IntegrationTestBase {

    @Test
    @DisplayName("Full happy path for POST /api/v1/employee")
    void scenario1() {
        WiremockStubs.stubPostEmployeeResourceServerOKResponse();
        testClient.post()
                .uri("/api/v1/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDummyRequest())
                .exchange()
                .expectStatus().isCreated()
                .expectBody(IdResponse.class)
                .value(response -> {
                    assert response != null;
                    assert response.id().equals("1");
                    assert response.message().equals("Employee created");
                });
    }

    @Test
    @DisplayName("Full happy path for GET /api/v1/employee/{id}")
    void scenario2() {
        WiremockStubs.stubGetEmployeeResourceServerOKResponse();
        testClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employee/{id}")
                        .build(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeResponse.class)
                .value(response -> {
                    assert response != null;
                    assert response.id() == 1;
                    assert response.certifications() != null;
                    assert response.certifications().size() == 2;
                    assert response.employer() != null;
                    assert response.employer().name().equals("Big International Firm");
                });
    }

    @Test
    @DisplayName("Full happy path for PUT /api/v1/employee/{id}")
    void scenario3() {
        WiremockStubs.stubPutEmployeeResourceServerOKResponse();
        testClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employee/{id}")
                        .build(1))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createDummyRequest())
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeResponse.class)
                .value(response -> {
                    assert response != null;
                    assert response.id() == 1;
                    assert response.certifications() != null;
                    assert response.certifications().size() == 2;
                    assert response.employer() != null;
                    assert response.employer().name().equals("Big International Firm");
                });
    }

    @Test
    @DisplayName("Full happy path for DELETE /api/v1/employee/{id}")
    void scenario4() {
        WiremockStubs.stubDeleteEmployeeResourceServerOKResponse();
        testClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employee/{id}")
                        .build(1))
                .exchange()
                .expectStatus().isOk()
                .expectBody(IdResponse.class)
                .value(response -> {
                    assert response != null;
                    assert response.id().equals("1");
                    assert response.message().equals("Employee deleted");
                });
    }

    @Test
    @DisplayName("Employee NOT FOUND for DELETE /api/v1/employee/{id}")
    void scenario5() {
        WiremockStubs.stubDeleteEmployeeResourceServerNotFoundResponse();
        testClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/employee/{id}")
                        .build(1))
                .exchange()
                .expectStatus().isNotFound();
    }

    private EmployeeRequest createDummyRequest() {
        return EmployeeRequest.builder()
                .firstName("Firstname")
                .lastName("Lastname")
                .email("test@test.no")
                .position("Principal Engineer")
                .employerId(1)
                .certifications(List.of(CertificationRequest.builder()
                        .name("AWS Cloud Practitioner")
                        .authority("Amazon")
                        .dateEarned(LocalDate.of(2024, 5, 19))
                        .dateEarned(LocalDate.of(2024, 7, 8))
                        .build()))
                .build();
    }
}
