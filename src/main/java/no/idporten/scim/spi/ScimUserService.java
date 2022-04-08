package no.idporten.scim.spi;


import no.idporten.scim.api.*;

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
    ScimUserResource updateUserLogins(String id, UpdateUserLoginRequest request);

    // User status API
    ScimUserResource updateUserStatus(String id, UpdateUserStatusRequest request);
    ScimUserResource changePersonIdentifier(ChangePersonIdentifierRequest request);

}
