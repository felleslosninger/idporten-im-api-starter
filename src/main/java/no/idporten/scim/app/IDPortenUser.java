package no.idporten.scim.app;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.idporten.scim.sdk.domain.ScimAttribute;
import no.idporten.scim.sdk.domain.ScimUniqueIdentifier;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * A domain class in ID-porten
 */
@Data
@NoArgsConstructor
public class IDPortenUser {

    @ScimUniqueIdentifier
    @ScimAttribute(schema = "urn:ietf:params:scim:schemas:core:2.0:User", attributeName = "userName")
    private String id;

    @ScimAttribute(schema = "urn:ietf:params:scim:schemas:core:2.0:User", attributeName = "userName")
    private String pid;

    @ScimAttribute(schema = "urn:ietf:params:scim:schemas:core:2.0:User", attributeName = "active")
    private boolean active;

    @ScimAttribute(schema = "urn:ietf:params:scim:schemas:extension:idporten:2.0:User", attributeName = "idporten-ejournal-references")
    private List<String> ejournalReferanse;

    // Sperrekoder: https://github.com/felleslosninger/eid-common/blob/master/kontaktinfo-lib-common/src/main/java/no/difi/kontaktregister/dto/DeleteUsersCause.java
    @ScimAttribute(schema = "urn:ietf:params:scim:schemas:extension:status:2.0:User", attributeName = "closed-code")
    private String closedCode;

    @ScimAttribute(schema = "urn:ietf:params:scim:schemas:extension:status:2.0:User", attributeName = "closed-date")
    private ZonedDateTime closedDate;


    @ScimAttribute(schema = "meta", attributeName = "created")
    private ZonedDateTime idportenCreatedDate;

    @ScimAttribute(schema = "meta", attributeName = "lastModified")
    private ZonedDateTime idportenLastUpdatedDate;




}
