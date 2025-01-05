package no.stackcanary.javaspringoauth2client.error;

public class ApplicationRuntimeException extends RuntimeException{
    public ApplicationRuntimeException(Throwable t) {
        super(t);
    }
}
