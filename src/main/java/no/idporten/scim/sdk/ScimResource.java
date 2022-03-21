package no.idporten.scim.sdk;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import no.idporten.scim.sdk.schema.Schema;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ScimResource {

    private List<URI> schemas;

    @JsonAnySetter
    private Map<String, Object> attributes = new HashMap<>();

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public Object getAttribute(Schema schema, String name) {
        if (schema.isCoreSchema()) {
            return getAttribute(name);
        }
        Map<String, Object> extesionAttributes = (Map<String, Object>) attributes.get(schema.getId().toString());
        if (extesionAttributes == null) {
            return null;
        }
        return extesionAttributes.get(name);
    }

    public Map<String, Object> getAttributes(Schema schema) {
        Map<String, Object> schemaBaseMap;
        if (schema.isCoreSchema()) {
            schemaBaseMap = attributes;
        } else {
            schemaBaseMap = (Map<String, Object>) attributes.get(schema.getId().toString());
        }
        if (schemaBaseMap == null) {
            return Collections.emptyMap();
        }
        return schema.getAttributes().stream()
                .filter(attribute -> schemaBaseMap.get(attribute.getName()) != null)
                .collect(Collectors.toMap(attribute -> attribute.getName(), attribute -> schemaBaseMap.get(attribute.getName())));
    }

}
