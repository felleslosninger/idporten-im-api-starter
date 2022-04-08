package no.idporten.scim.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateLoginRequest {

    @NotEmpty
    @JsonProperty("eid_name")
    private String personIdentifier;

}
