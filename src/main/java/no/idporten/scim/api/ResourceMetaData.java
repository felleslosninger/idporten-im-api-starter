package no.idporten.scim.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class ResourceMetaData {

    @JsonProperty("created")
    private ZonedDateTime created;

    @JsonProperty("last_modified")
    private ZonedDateTime lastModified;


}
