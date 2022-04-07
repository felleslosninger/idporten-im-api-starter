package no.idporten.scim.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScimUserResource {

    private boolean active;

    private String id;

    @JsonProperty("person_identifier")
    private String personIdentifier;

    @JsonProperty("closed_code")
    private String closedCode;

}
