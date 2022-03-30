package no.idporten.scim.sdk;

import java.util.Collections;
import java.util.List;

public class ScimErrorResponse {

    public ScimErrorResponse(String detail, String status) {
        this.detail = detail;
        this.status = status;
    }

    private List<String> schemas = Collections.singletonList("urn:ietf:params:scim:api:messages:2.0:Error");
    private String detail;
    private String status;

}
