package no.idporten.scim.sdk.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.DefaultResourceLoader;

import java.net.URI;
import java.util.*;

public class ScimSchemaRegistry {

    private Map<URI, Schema> schemaMap = new HashMap<>();

    public Schema getSchema(URI schemaUri) {
        return Optional.ofNullable(schemaMap.get(schemaUri)).orElseThrow(() -> new IllegalArgumentException("Unknown schema " + schemaUri));
    }

    @SneakyThrows
    public void init() {
        List<String> schemaLocations = Arrays.asList("classpath:/schema/UserSchema.json", "classpath:/schema/IDportenUserSchema.json");
        ObjectMapper objectMapper = new ObjectMapper();
        for (String schemaLocation : schemaLocations) {
            Schema schema = objectMapper.readValue(new DefaultResourceLoader().getResource(schemaLocation).getFile(), Schema.class);
            schemaMap.put(schema.getId(), schema);
        }
    }

}
