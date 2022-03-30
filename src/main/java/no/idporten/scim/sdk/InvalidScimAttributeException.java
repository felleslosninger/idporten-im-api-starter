package no.idporten.scim.sdk;

import no.idporten.scim.sdk.schema.Attribute;
import no.idporten.scim.sdk.schema.Schema;

public class InvalidScimAttributeException extends ScimException {

    public InvalidScimAttributeException(String message) {
        super(message);
    }

    public static InvalidScimAttributeException missingAttributeValue(Attribute attribute, Schema schema) {
        return new InvalidScimAttributeException(String.format("Missing required attribute [%s] in schema [%s]", attribute.getName(), schema.getId()));
    }

}
