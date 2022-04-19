package no.idporten.im.api;

import lombok.extern.slf4j.Slf4j;
import no.idporten.im.IdentityManagementApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice("no.idporten.im.api")
public class IdentityManagementApiExceptionHandler {

    @ExceptionHandler(IdentityManagementApiException.class)
    public ResponseEntity<ErrorResponse> handleIMApiException(IdentityManagementApiException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getError(), e.getErrorDescription()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Failed to handle request", e);
        return ResponseEntity.internalServerError().body(new ErrorResponse("internal_server_error", "Failed to handle request. See server logs for details"));
    }

}
