package no.idporten.im.api.status;

import no.idporten.im.api.UserResource;
import no.idporten.im.spi.IDPortenIdentityManagementUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnBean(type = "no.idporten.im.spi.IDPortenIdentityManagementUserService")
public class UserStatusApiController {

    private final IDPortenIdentityManagementUserService identityManagementUserService;

    @Autowired
    public UserStatusApiController(IDPortenIdentityManagementUserService identityManagementUserService) {
        this.identityManagementUserService = identityManagementUserService;
    }

    @PutMapping(path = "/iim/v1/users/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUserStatus(@PathVariable("id") String id, @RequestBody UpdateUserStatusRequest request) {
        return ResponseEntity.ok(identityManagementUserService.updateUserStatus(id, request));
    }

    @PutMapping(path = "/iim/v1/users/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUserStatus(@RequestBody ChangePersonIdentifierRequest request) {
        return ResponseEntity.ok(identityManagementUserService.changePersonIdentifier(request));
    }

}
