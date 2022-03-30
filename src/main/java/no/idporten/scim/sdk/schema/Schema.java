package no.idporten.scim.sdk.schema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URI;
import java.util.List;
import java.util.Objects;

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

    public Attribute getAttribute(String name) {
        return attributes.stream()
                .filter(attribute -> Objects.equals(name, attribute.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown attribute [%s] in schema [%s]", name, id)));
    }

}
