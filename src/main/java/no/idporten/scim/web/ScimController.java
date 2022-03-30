package no.idporten.scim.web;

import lombok.RequiredArgsConstructor;
import no.idporten.scim.app.IDPortenUser;
import no.idporten.scim.sdk.ScimErrorResponse;
import no.idporten.scim.sdk.ScimResource;
import no.idporten.scim.sdk.ScimResourceHandler;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.*;

@RequiredArgsConstructor
public class ScimController {

    private final ScimResourceHandler scimResourceHandler;

    private Map<String, IDPortenUser> userMap = new HashMap<>();


    // TODO type from path
    public Object get(String id) {
        IDPortenUser idPortenUser = userMap.get(id);
        if (idPortenUser == null) {
            return new ScimErrorResponse("Resource not found", "404");
        }
        return scimResourceHandler.convert(idPortenUser, Arrays.asList(URI.create("urn:ietf:params:scim:schemas:extension:idporten:2.0:User"), URI.create("urn:ietf:params:scim:schemas:core:2.0:User"))); // TODO schemas må beregnes?
   }

   public ScimResource post(ScimResource scimResource) {
        IDPortenUser idPortenUser = scimResourceHandler.convert(scimResource, IDPortenUser.class);
        idPortenUser.setId(UUID.randomUUID().toString());
        userMap.put(idPortenUser.getId(), idPortenUser);
       return scimResourceHandler.convert(idPortenUser, scimResource.getSchemas()); // TODO schemas må beregnes?
   }

}
