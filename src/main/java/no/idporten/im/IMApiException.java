package no.idporten.im;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IMApiException extends RuntimeException {

    private String error;
    private String errorDescription;
    private HttpStatus httpStatus;

    public IMApiException(String error, String errorDescription, HttpStatus httpStatus) {
        this.error = error;
        this.errorDescription = errorDescription;
        this.httpStatus = httpStatus;
    }

}
