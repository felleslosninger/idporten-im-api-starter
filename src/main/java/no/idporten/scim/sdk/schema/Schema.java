package no.idporten.scim.sdk.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URI;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Schema {

    private URI id;
    private String name;
    private String description;
    private List<Attribute> attributes;
    private Meta meta;

    public boolean isExtensionSchema() {
        return id.getSchemeSpecificPart().contains("extension");
    }

    public boolean isCoreSchema() {
        return id.getSchemeSpecificPart().contains("core");
    }

}
