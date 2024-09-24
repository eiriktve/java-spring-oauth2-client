package no.stackcanary.javaspringoauth2client.error;

public class ResourceServerException extends RuntimeException{
    public ResourceServerException(String message, Throwable t) {
        super(message, t);
    }
}
