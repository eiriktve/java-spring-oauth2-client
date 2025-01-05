package no.stackcanary.javaspringoauth2client.resource.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Employee(
        int id,
        String firstName,
        String lastName,
        String email,
        String position,
        Company employer,
        List<Certification> certifications
) {}
