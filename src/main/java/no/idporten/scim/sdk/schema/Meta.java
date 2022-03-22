package no.idporten.scim.sdk.schema;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URI;
import java.time.ZonedDateTime;

/**
 * Server-assigned metadata about a scim resource - https://datatracker.ietf.org/doc/html/rfc7643#section-3.1 Common Attributes
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {

    private String resourceType;
    private URI location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private ZonedDateTime created;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private ZonedDateTime lastModified;

}
