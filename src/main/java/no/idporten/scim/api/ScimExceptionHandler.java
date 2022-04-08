package no.idporten.scim.api;

import lombok.extern.slf4j.Slf4j;
import no.idporten.scim.ScimException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice("no.idporten.scim.api")
public class ScimExceptionHandler {

    @ExceptionHandler(ScimException.class)
    public ResponseEntity<ErrorResponse> handleScimException(ScimException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponse(e.getError(), e.getErrorDescription()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Failed to handle request", e);
        return ResponseEntity.internalServerError().body(new ErrorResponse("internal_server_error", "Failed to handle request. See server logs for details"));
    }

}
