package no.idporten.im.api.login;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserLoginRequest {

    @NotEmpty
    @JsonProperty("eid_name")
    private String eidName;

}
