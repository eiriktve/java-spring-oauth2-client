package no.stackcanary.javaspringoauth2client.resource.dto.response;

import lombok.Builder;

@Builder
public record CompanyResponse(
        Integer id,
        String name,
        String businessArea
) {}
