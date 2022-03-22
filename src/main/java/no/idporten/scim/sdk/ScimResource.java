package no.idporten.scim.sdk;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import no.idporten.scim.sdk.schema.Schema;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ScimResource {

    private List<URI> schemas = new ArrayList<>();

    public ScimResource() {
    }

    @JsonAnySetter
    @JsonAnyGetter
    @JsonIgnore
    private Map<String, Object> _attributes = new HashMap<>();


    @JsonIgnore
    public Map<String, Object> getAttributes() {
        return _attributes;
    }

    @JsonIgnore
    public Object getAttribute(String name) {
        return _attributes.get(name);
    }

    @JsonIgnore
    public Object getAttribute(Schema schema, String name) {
        if (schema.isCoreSchema()) {
            return getAttribute(name);
        }
        Map<String, Object> extesionAttributes = (Map<String, Object>) _attributes.get(schema.getId().toString());
        if (extesionAttributes == null) {
            return null;
        }
        return extesionAttributes.get(name);
    }

    @JsonIgnore
    public Map<String, Object> getAttributes(Schema schema) {
        Map<String, Object> schemaBaseMap;
        if (schema.isCoreSchema()) {
            schemaBaseMap = _attributes;
        } else {
            schemaBaseMap = (Map<String, Object>) _attributes.get(schema.getId().toString());
        }
        if (schemaBaseMap == null) {
            return new HashMap<>();
        }
        return schema.getAttributes().stream()
                .filter(attribute -> schemaBaseMap.get(attribute.getName()) != null)
                .collect(Collectors.toMap(attribute -> attribute.getName(), attribute -> schemaBaseMap.get(attribute.getName())));
    }

    @JsonIgnore
    public void setAttribute(Schema schema, String name, Object value) {
        if (schema.isCoreSchema()) {
            getAttributes().put(name, value);
        }
        if (schema.isExtensionSchema()) {
            if (! getAttributes().containsKey(schema.getId().toString())) {
                getAttributes().put(schema.getId().toString(), new HashMap<String, Object>());
            }
            ((Map<String, Object>)getAttributes().get(schema.getId().toString())).put(name, value);
        }
    }

}
