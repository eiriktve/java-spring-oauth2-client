package no.stackcanary.javaspringoauth2client.resource.model;

import java.util.List;

public record Employee(
        int id,
        String firstName,
        String lastName,
        String email,
        String position,
        Company employer,
        List<Certification> certifications
) {
    public Employee(int id, String firstName, String lastName, String email, String position, Company employer) {
        this(id, firstName, lastName, email, position, employer, List.of());
    }
}
