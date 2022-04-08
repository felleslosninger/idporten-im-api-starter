package no.idporten.scim.api.status;

import no.idporten.scim.api.ChangePersonIdentifierRequest;
import no.idporten.scim.api.ScimUserResource;
import no.idporten.scim.api.UpdateUserStatusRequest;
import no.idporten.scim.spi.ScimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnBean(type = "no.idporten.scim.spi.ScimUserService")
public class UserStatusApiController {

    private static final String APPLICATION_SCIM_JSON = "application/scim+json";

    private final ScimUserService scimUserService;

    @Autowired
    public UserStatusApiController(ScimUserService scimUserService) {
        this.scimUserService = scimUserService;
    }

    @PutMapping(path = "/scim/v2/users/{id}/status", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<ScimUserResource> updateUserStatus(@PathVariable("id") String id, @RequestBody UpdateUserStatusRequest request) {
        return ResponseEntity.ok(scimUserService.updateUserStatus(id, request));
    }

    @PutMapping(path = "/scim/v2/users/status", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<ScimUserResource> updateUserStatus(@RequestBody ChangePersonIdentifierRequest request) {
        return ResponseEntity.ok(scimUserService.changePersonIdentifier(request));
    }

}
