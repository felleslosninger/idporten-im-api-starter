package no.idporten.im.spi;


import no.idporten.im.api.*;
import no.idporten.im.api.admin.UpdateAttributesRequest;
import no.idporten.im.api.login.CreateUserRequest;
import no.idporten.im.api.login.UpdateUserLoginRequest;
import no.idporten.im.api.admin.ChangeIdentifierRequest;
import no.idporten.im.api.admin.UpdateStatusRequest;

import java.util.List;

/**
 * Interface between the api starter and the application.
 */
public interface IDPortenIdentityManagementUserService {

    // Search/lookup API
    UserResource lookup(String id);
    List<UserResource> searchForUser(String pid);

    // Login API
    UserResource createUser(CreateUserRequest request);
    UserResource updateUserLogins(String id, UpdateUserLoginRequest request);

    // User admin API
    UserResource updateUserAttributes(String id, UpdateAttributesRequest request);
    UserResource updateUserStatus(String id, UpdateStatusRequest request);
    UserResource changePersonIdentifier(ChangeIdentifierRequest request);

}
