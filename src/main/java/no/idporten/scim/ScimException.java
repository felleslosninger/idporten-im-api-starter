package no.idporten.scim;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ScimException extends RuntimeException {

    private String error;
    private String errorDescription;
    private HttpStatus httpStatus;

    public ScimException(String error, String errorDescription, HttpStatus httpStatus) {
        this.error = error;
        this.errorDescription = errorDescription;
        this.httpStatus = httpStatus;
    }

}
