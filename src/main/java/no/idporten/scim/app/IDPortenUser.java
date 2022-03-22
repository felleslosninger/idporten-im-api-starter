package no.idporten.scim.app;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.idporten.scim.sdk.domain.ScimProperty;

import java.time.ZonedDateTime;

/**
 * A domain class in ID-porten
 */
@Data
@NoArgsConstructor
public class IDPortenUser {

    @ScimProperty(schema = "urn:ietf:params:scim:schemas:core:2.0:User", attributeName = "active")
    private boolean active;

    @ScimProperty(schema = "urn:ietf:params:scim:schemas:core:2.0:User", attributeName = "userName")
    private String pid;

    @ScimProperty(schema = "urn:ietf:params:scim:schemas:extension:idporten:2.0:User", attributeName = "idporten-special-attribute")
    private String extra;

    // TODO håndtere metadata som assignes av server og ikke skal endres bedre
    @ScimProperty(schema = "meta", attributeName = "created")
    private ZonedDateTime idportenCreatedDate;

}
