package no.idporten.scim.app;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.idporten.scim.sdk.domain.ScimProperty;

/**
 * A domain class in ID-porten
 */
@Data
@NoArgsConstructor
public class IDPortenUser {

    @ScimProperty(schema = "urn:ietf:params:scim:schemas:core:2.0:User", attributeName = "userName")
    private String pid;

    @ScimProperty(schema = "urn:ietf:params:scim:schemas:extension:idporten:2.0:User", attributeName = "idporten-special-attribute")
    private String extra;

}
