package no.idporten.scim.sdk;

public class ScimException extends RuntimeException {

    public ScimException(String message) {
        super(message);
    }

    public ScimException(String message, Throwable cause) {
        super(message, cause);
    }
}
