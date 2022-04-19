package no.idporten.im;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IdentityManagementApiException extends RuntimeException {

    private String error;
    private String errorDescription;
    private HttpStatus httpStatus;

    public IdentityManagementApiException(String error, String errorDescription, HttpStatus httpStatus) {
        this.error = error;
        this.errorDescription = errorDescription;
        this.httpStatus = httpStatus;
    }

}
