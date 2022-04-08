package no.idporten.scim.api.login;

import no.idporten.scim.api.CreateUserRequest;
import no.idporten.scim.api.ScimUserResource;
import no.idporten.scim.api.SearchRequest;
import no.idporten.scim.api.UpdateUserLoginRequest;
import no.idporten.scim.spi.ScimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * API for checking user status, creating user at first login, and updating logins.
 */
@RestController
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnBean(type = "no.idporten.scim.spi.ScimUserService")
public class ScimLoginAPIController {

    private static final String APPLICATION_SCIM_JSON = "application/scim+json";

    private final ScimUserService scimUserService;

    @Autowired
    public ScimLoginAPIController(ScimUserService scimUserService) {
        this.scimUserService = scimUserService;
    }

    /**
     * Search for a user by person identifier
     *
     * @param searchRequest search request
     * @return list of found users
     */
    @PostMapping(path = "/scim/v2/login/users/search", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<List<ScimUserResource>> searchUser(@Valid @RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(scimUserService.searchForUser(searchRequest.getPersonIdentifier()));
    }

    /**
     * Creates a new user.
     *
     * @param request new user
     * @return created user
     */
    @PostMapping(path = "/scim/v2/login/users/", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<ScimUserResource> createUser(@Valid @RequestBody CreateUserRequest request) {
         return ResponseEntity.ok(scimUserService.createUserOnFirstLogin(request));
    }

    /**
     * Adds a user login to a user.
     *
     * @param userId user id
     * @param request new user login
     * @return updates user
     */
    @PostMapping(path = "/scim/v2/login/users/{id}/logins", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<ScimUserResource> updateUserLogins(@PathVariable("id") String userId, @Valid @RequestBody UpdateUserLoginRequest request) {
        return ResponseEntity.ok(scimUserService.updateUserLogins(userId, request));
    }

}
