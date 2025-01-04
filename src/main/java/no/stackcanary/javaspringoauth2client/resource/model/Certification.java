package no.stackcanary.javaspringoauth2client.resource.model;

import java.time.LocalDate;

public record Certification(
        int id,
        String name,
        String authority,
        LocalDate dateEarned,
        LocalDate expiryDate
) {}
