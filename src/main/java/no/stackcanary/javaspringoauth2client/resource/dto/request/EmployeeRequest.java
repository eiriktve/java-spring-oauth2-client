package no.stackcanary.javaspringoauth2client.resource.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record EmployeeRequest(
        String firstName,
        String lastName,
        String email,
        String position,
        Integer employerId,
        List<CertificationRequest> certifications
) {}
