package no.idporten.scim.sdk;

import lombok.SneakyThrows;
import no.idporten.scim.sdk.domain.ScimAttribute;
import no.idporten.scim.sdk.domain.ScimUniqueIdentifier;
import no.idporten.scim.sdk.schema.Attribute;
import no.idporten.scim.sdk.schema.Meta;
import no.idporten.scim.sdk.schema.Schema;
import no.idporten.scim.sdk.schema.ScimSchemaRegistry;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ScimResourceHandler {

    private final ScimSchemaRegistry schemaRegistry;
    private final DateTimeFormatter dateTimeFormatter;

    public ScimResourceHandler(ScimSchemaRegistry schemaRegistry) {
        this.schemaRegistry = schemaRegistry;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }

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
                throw InvalidScimAttributeException.missingAttributeValue(attribute, schema);
            }
            if (attribute.isMultiValued()) {

            }
            // TODO sjekke egenskaper ved attributtet, som type, lov å sende inn, etc
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
                    ScimAttribute scimProperty = field.getAnnotation(ScimAttribute.class);
                    if (! "meta".equals(scimProperty.schema())) {
                        Schema schema = schemaRegistry.getSchema(URI.create(scimProperty.schema()));
                        ReflectionUtils.setField(field, instance, scimResource.getAttribute(schema, scimProperty.attributeName()));
                    }
                },
                field -> field.isAnnotationPresent(ScimAttribute.class)
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
        Meta meta = new Meta();
        meta.setResourceType("User");
        meta.setLocation(URI.create("https://localhost:8080/scim/v2/Schemas/" + schemas.get(0))); // TODO finn rette
        scimResource.setMeta(meta);
        ReflectionUtils.doWithFields(object.getClass(),
                field -> {
                    field.setAccessible(true);
                    final Object value = field.get(object);
                    if (field.isAnnotationPresent(ScimUniqueIdentifier.class)) {
                        scimResource.setId(String.valueOf(value));
                    }

                    ScimAttribute scimProperty = field.getAnnotation(ScimAttribute.class);
                    if ("meta".equals(scimProperty.schema())) { // TODO meta må håndteres bedre!
                        Field metaField = ReflectionUtils.findField(Meta.class, scimProperty.attributeName());
                        metaField.setAccessible(true);
                        metaField.set(meta, value);
                    } else {
                        Schema schema = schemaRegistry.getSchema(URI.create(scimProperty.schema()));
                        Attribute attribute = schema.getAttribute(scimProperty.attributeName());
                        if (attribute.isRequired() && value == null) {
                            throw InvalidScimAttributeException.missingAttributeValue(attribute, schema);
                        }
                        if (value == null) {
                            return;
                        }
                        if (attribute.isMultiValued()) {
                            // TODO typer
                            scimResource.setAttribute(schema, attribute.getName(), (List) value);
                        }
                        if (Attribute.Type.dateTime == attribute.getType()) {
                            scimResource.setAttribute(schema, attribute.getName(), ((ZonedDateTime) value).format(dateTimeFormatter));
                        }  else {
                            scimResource.setAttribute(schema, scimProperty.attributeName(), field.get(object));
                        }
                    }

                },
                field -> field.isAnnotationPresent(ScimAttribute.class) || field.isAnnotationPresent(ScimUniqueIdentifier.class)
        );
        return scimResource;
    }

}
