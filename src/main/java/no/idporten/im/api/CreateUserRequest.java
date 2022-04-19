package no.idporten.im.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {

    @NotEmpty
    @JsonProperty("person_identifier")
    private String personIdentifier;


}
