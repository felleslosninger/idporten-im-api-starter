package no.idporten.im.api.status;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserStatusRequest {

    @NotEmpty
    @JsonProperty("closed_code")
    private String closedCode;

}
