package no.stackcanary.javaspringoauth2client.error;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import no.stackcanary.javaspringoauth2client.resource.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandlerAdvice {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> fallBackHandler(Throwable e) {
        log.error("Entered fallback handler with error message: {}", e.getLocalizedMessage(), e);

        val error = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .error(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, new HttpHeaders(), error.status());
    }

    @ExceptionHandler(ResourceServerException.class)
    public ResponseEntity<ErrorResponse> resourceServerExceptionHandler(ResourceServerException e) {
        log.error("Advice caught ResourceServerException with error message: {}", e.getLocalizedMessage(), e);

        val error = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An error occurred when interacting with the resource server")
                .error(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, new HttpHeaders(), error.status());
    }
}
