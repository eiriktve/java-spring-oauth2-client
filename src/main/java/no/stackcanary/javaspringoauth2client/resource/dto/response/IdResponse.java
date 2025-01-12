package no.stackcanary.javaspringoauth2client.resource.dto.response;

import lombok.Builder;

@Builder
public record IdResponse(
        String id,
        String message
) {}
