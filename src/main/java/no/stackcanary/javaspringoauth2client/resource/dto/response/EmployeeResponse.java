package no.stackcanary.javaspringoauth2client.resource.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record EmployeeResponse(
        int id,
        String firstName,
        String lastName,
        String email,
        String position,
        CompanyResponse employer,
        List<CertificationResponse> certifications
) {}
