package no.idporten.im.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResource {

    private boolean active;

    private String id;

    @JsonProperty("person_identifier")
    private String personIdentifier;

    @JsonProperty("status")
    private UserStatus userStatus;

    @JsonProperty("logins")
    private List<UserLogin> userLogins = new ArrayList<>();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("created")
    private ZonedDateTime created;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonProperty("last_modified")
    private ZonedDateTime lastModified;

}
