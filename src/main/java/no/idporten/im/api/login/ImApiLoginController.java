package no.idporten.im.api.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import no.idporten.im.api.UserResource;
import no.idporten.im.api.SearchRequest;
import no.idporten.im.spi.IDPortenIdentityManagementUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * API for checking user status, creating user at first login, and updating logins.
 */
@Tag(name = "login-api", description = "API for login services")
@Slf4j
@RestController
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnBean(type = "no.idporten.im.spi.IDPortenIdentityManagementUserService")
public class ImApiLoginController {

    private final IDPortenIdentityManagementUserService identityManagementUserService;

    @Autowired
    public ImApiLoginController(IDPortenIdentityManagementUserService identityManagementUserService) {
        this.identityManagementUserService = identityManagementUserService;
    }

    /**
     * Search for a user.
     *
     * @param searchRequest search request
     * @return list of found users
     */
    @Operation(summary = "Search for users", description = "Search for users using external references", tags = {"login-api"})
    @PostMapping(path = "/im/v1/login/users/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResource>> searchUser(@Valid @RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(identityManagementUserService.searchForUser(searchRequest.getPersonIdentifier()));
    }

    /**
     * Creates a new user.
     *
     * @param request new user
     * @return created user
     */
    @Operation(summary = "Create a new user", description = "Create a new user", tags = {"login-api"})
    @PostMapping(path = "/im/v1/login/users/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody CreateUserRequest request) {
         return ResponseEntity.ok(identityManagementUserService.createUserOnFirstLogin(request));
    }

    /**
     * Adds a user login to a user.
     *
     * @param userId user id
     * @param request new user login
     * @return updates user
     */
    @Operation(summary = "Update user logins", description = "Update user logins with a new login", tags = {"login-api"})
    @PostMapping(path = "/im/v1/login/users/{id}/logins", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUserLogins(@PathVariable("id") String userId, @Valid @RequestBody UpdateUserLoginRequest request) {
        return ResponseEntity.ok(identityManagementUserService.updateUserLogins(userId, request));
    }

}
