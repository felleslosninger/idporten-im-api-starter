package no.idporten.scim.spi;


import no.idporten.scim.api.UpdateUserStatusRequest;
import no.idporten.scim.api.CreateUserRequest;
import no.idporten.scim.api.ScimUserResource;
import no.idporten.scim.api.UpdateLoginDetailsRequest;

import java.util.List;

/**
 * Interface between the api starter and the application.
 */
public interface ScimUserService {

    // Search/lookup API
    ScimUserResource lookup(String id);
    List<ScimUserResource> searchForUser(String pid);

    // Login API
    ScimUserResource createUserOnFirstLogin(CreateUserRequest request);
    ScimUserResource updateLoginDetails(String id, UpdateLoginDetailsRequest request);

    // User status API
    ScimUserResource updateUserStatus(String id, UpdateUserStatusRequest request);

}
