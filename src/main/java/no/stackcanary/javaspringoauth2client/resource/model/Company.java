package no.stackcanary.javaspringoauth2client.resource.model;

import lombok.Builder;

@Builder
public record Company(
        int id,
        String name,
        String businessArea
) {}
