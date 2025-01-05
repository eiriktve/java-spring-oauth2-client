package no.stackcanary.javaspringoauth2client.resource.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Certification(
        int id,
        String name,
        String authority,
        LocalDate dateEarned,
        LocalDate expiryDate
) {}
