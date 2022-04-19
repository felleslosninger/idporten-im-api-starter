package no.idporten.im.spi;


import no.idporten.im.api.*;
import no.idporten.im.api.login.CreateUserRequest;
import no.idporten.im.api.login.UpdateUserLoginRequest;
import no.idporten.im.api.status.ChangePersonIdentifierRequest;
import no.idporten.im.api.status.UpdateUserStatusRequest;

import java.util.List;

/**
 * Interface between the api starter and the application.
 */
public interface IDPortenIdentityManagementUserService {

    // Search/lookup API
    UserResource lookup(String id);
    List<UserResource> searchForUser(String pid);

    // Login API
    UserResource createUserOnFirstLogin(CreateUserRequest request);
    UserResource updateUserLogins(String id, UpdateUserLoginRequest request);

    // User status API
    UserResource updateUserStatus(String id, UpdateUserStatusRequest request);
    UserResource changePersonIdentifier(ChangePersonIdentifierRequest request);

}
