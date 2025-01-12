package no.stackcanary.javaspringoauth2client.resource.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CertificationRequest(
        String name,
        String authority,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dateEarned,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate expiryDate
) {}
