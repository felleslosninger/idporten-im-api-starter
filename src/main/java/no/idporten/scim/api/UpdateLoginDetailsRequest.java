package no.idporten.scim.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateLoginDetailsRequest {

    @JsonProperty("eid_name")
    private String eidName;

}
