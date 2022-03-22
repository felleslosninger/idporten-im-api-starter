package no.idporten.scim.sdk;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import no.idporten.scim.sdk.domain.ScimProperty;
import no.idporten.scim.sdk.schema.Attribute;
import no.idporten.scim.sdk.schema.Schema;
import no.idporten.scim.sdk.schema.ScimSchemaRegistry;
import org.springframework.util.ReflectionUtils;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ScimResourceHandler {

    private final ScimSchemaRegistry schemaRegistry;

    /**
     * Validate a scim resource with the schemas it uses.
     */
    public void validate(ScimResource scimResource) {
        for (URI schemaUri : scimResource.getSchemas()) {
            Schema schema = schemaRegistry.getSchema(schemaUri);
            validate(schema, scimResource.getAttributes(schema));
        }
    }

    protected void validate(Schema schema, Map<String, Object> attributes) {
        for (Attribute attribute : schema.getAttributes()) {
            Object value = attributes.get(attribute.getName());
            if (value == null && attribute.isRequired()) {
                throw new IllegalArgumentException(String.format("Missing required attribute [%s] from schema [%s]", attribute.getName(), schema.getId()));
            }
        }
    }

    /**
     * Convert a scim resource to a domain class.
     */
    @SneakyThrows
    public <T> T convert(ScimResource scimResource, Class<T> clazz) {
        T instance = clazz.newInstance();
        ReflectionUtils.doWithFields(clazz,
                field -> {
                    field.setAccessible(true);
                    ScimProperty scimProperty = field.getAnnotation(ScimProperty.class);
                    Schema schema = schemaRegistry.getSchema(URI.create(scimProperty.schema()));
                    ReflectionUtils.setField(field, instance, scimResource.getAttribute(schema, scimProperty.attributeName()));
                },
                field -> field.isAnnotationPresent(ScimProperty.class)
        );
        return instance;
    }

    /**
     * Convert a domain class to a scim resource
     * @param object
     * @param schemas
     * @return
     */
    public ScimResource convert(Object object, List<URI> schemas) {
        ScimResource scimResource = new ScimResource();
        scimResource.setSchemas(schemas);
        ReflectionUtils.doWithFields(object.getClass(),
                field -> {
                    field.setAccessible(true);
                    ScimProperty scimProperty = field.getAnnotation(ScimProperty.class);
                    Schema schema = schemaRegistry.getSchema(URI.create(scimProperty.schema()));
                    scimResource.setAttribute(schema, scimProperty.attributeName(), field.get(object));
                },
                field -> field.isAnnotationPresent(ScimProperty.class)
        );
        return scimResource;
    }

}
