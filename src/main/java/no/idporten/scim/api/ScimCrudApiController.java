package no.idporten.scim.api;

import no.idporten.scim.spi.ScimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnBean(type = "no.idporten.scim.spi.ScimUserService")
public class ScimCrudApiController {

    private static final String APPLICATION_SCIM_JSON = "application/scim+json";

    private final ScimUserService scimUserService;

    @Autowired
    public ScimCrudApiController(ScimUserService scimUserService) {
        this.scimUserService = scimUserService;
    }

    /**
     * Get a user resource by id.  https://datatracker.ietf.org/doc/html/rfc7644#section-3.4.1
     * @param id server assigned id
     * @return user resource
     */
    @GetMapping(path = "/scim/v2/users/{id}", produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<ScimUserResource> lookupUser(@PathVariable("id") String id){
        ScimUserResource scimUserResource = scimUserService.lookup(id);
        if (scimUserResource != null) {
            return ResponseEntity.ok(scimUserResource);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Search for a user by pid.
     * @param userSearchRequest
     * @return
     */
    @PostMapping(path = "/scim/v2/users/search", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<List<ScimUserResource>> searchUser(@Valid @RequestBody SearchRequest userSearchRequest) {
        List<ScimUserResource> searchResult = scimUserService.searchForUser(userSearchRequest.getPersonIdentifier());
        return ResponseEntity.ok(searchResult);
    }

    @PostMapping(path = "/scim/v2/users/", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<ScimUserResource> createUser(@RequestBody CreateUserRequest request) {
         return ResponseEntity.ok(scimUserService.createUserOnFirstLogin(request));
    }

    @PostMapping(path = "/scim/v2/users/{id}/logindetails", consumes = APPLICATION_SCIM_JSON, produces = APPLICATION_SCIM_JSON)
    public ResponseEntity<ScimUserResource> updateLoginDetails(@PathVariable("id") String id, @RequestBody UpdateUserLoginRequest request) {
        return ResponseEntity.ok(scimUserService.updateUserLogins(id, request));
    }

}
