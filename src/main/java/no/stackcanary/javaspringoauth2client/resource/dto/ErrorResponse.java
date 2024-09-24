package no.stackcanary.javaspringoauth2client.resource.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp,
        String path
) {
    public ErrorResponse(int status, String error, String message, String path) {
        this(status, error, message, LocalDateTime.now(), path);
    }

    public ErrorResponse(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now(), null);
    }
}
