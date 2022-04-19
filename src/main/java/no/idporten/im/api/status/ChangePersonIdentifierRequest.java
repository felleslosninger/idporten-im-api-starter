package no.idporten.im.api.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePersonIdentifierRequest {

    @NotEmpty
    @JsonProperty("from_person_identifier")
    private String fromPersonIdentifier;

    @NotEmpty
    @JsonProperty("to_person_identifier")
    private String toPersonIdentifier;

}
