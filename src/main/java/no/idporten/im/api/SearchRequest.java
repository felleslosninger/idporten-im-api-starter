package no.idporten.im.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchRequest {

    @JsonProperty("person_identifier")
    private String personIdentifier;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobile")
    private String mobile;


}
