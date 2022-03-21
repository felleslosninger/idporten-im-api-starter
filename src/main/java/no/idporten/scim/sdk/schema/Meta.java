package no.idporten.scim.sdk.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URI;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    private String resourceType;
    private URI location;

}
