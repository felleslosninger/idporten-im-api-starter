package no.idporten.im.api.admin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeIdentifierRequest {

    @NotEmpty
    @JsonProperty("old_person_identifier")
    private String oldPersonIdentifier;

    @NotEmpty
    @JsonProperty("new_person_identifier")
    private String newPersonIdentifier;

}
