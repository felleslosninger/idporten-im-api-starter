package no.idporten.im.api.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import no.idporten.im.IdentityManagementApiException;
import no.idporten.im.api.SearchRequest;
import no.idporten.im.api.UserResource;
import no.idporten.im.spi.IDPortenIdentityManagementUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "admin-api", description = "User Service Admin API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(examples = {
                @ExampleObject(description = "Error response", value = ImApiAdminController.errorResponseExample)
        })),
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content(examples = {
                @ExampleObject(description = "Error response", value = ImApiAdminController.errorResponseExample)
        }))
})
@RestController
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ConditionalOnBean(type = "no.idporten.im.spi.IDPortenIdentityManagementUserService")
public class ImApiAdminController {

    public static final String errorResponseExample = "{\"error\": \"error code\", \"error_description\": \"description of the error\"}";

    private final IDPortenIdentityManagementUserService identityManagementUserService;

    @Autowired
    public ImApiAdminController(IDPortenIdentityManagementUserService identityManagementUserService) {
        this.identityManagementUserService = identityManagementUserService;
    }

    /**
     * Search for a user.
     *
     * @param searchRequest search request
     * @return list of found users
     */
    @Operation(
            summary = "Search for users",
            description = "Search for users using external references",
            tags = {"admin-api"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empty list if no user's are found")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users.  Empty list if no users are found")
    })
    @PostMapping(path = "/im/v1/users/.search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResource>> searchUser(@Valid @RequestBody SearchRequest searchRequest) {
        return ResponseEntity.ok(identityManagementUserService.searchForUser(searchRequest.getPersonIdentifier()));
    }

    @Operation(
            summary = "Retrieve a user",
            description = "Retrieve a user by internal id",
            tags = {"admin-api"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "User id")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is found"),
            @ApiResponse(responseCode = "404", description = "User is not found", content = @Content(examples = {
                    @ExampleObject(description = "Error response", value = errorResponseExample)
            }))
    })
    @GetMapping(path = "/im/v1/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> retrieveUser(@PathVariable("id") String id) {
        UserResource userResource = identityManagementUserService.lookup(id);
        if (userResource == null) {
            throw new IdentityManagementApiException("user_not_found", "User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userResource);
    }

    @Operation(
            summary = "Update attributes for a user",
            description = "Update user attributes",
            tags = {"admin-api"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "User id")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User is updated"),
            @ApiResponse(responseCode = "404", description = "User is not found")
    })
    @PatchMapping(path = "/im/v1/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUserAttributes(@PathVariable("id") String id,
                                                             @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated attributes", content = @Content(examples = {
                                                                     @ExampleObject(value = "{\"attribute1\": \"value1\", \"attribute2\": \"value2\"}")
                                                             })) UpdateAttributesRequest request) {
        return ResponseEntity.ok(identityManagementUserService.updateUserAttributes(id, request));
    }

    @Operation(
            summary = "Update status for a user",
            description = "Update user status",
            tags = {"admin-api"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", required = true, description = "User id")
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User status is updated"),
            @ApiResponse(responseCode = "404", description = "User is not found")
    })
    @PutMapping(path = "/im/v1/users/{id}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUserStatus(@PathVariable("id") String id, @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(identityManagementUserService.updateUserStatus(id, request));
    }

    @Operation(summary = "Change user identifier", description = "Change user identifier", tags = {"admin-api"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User identifier is updated"),
            @ApiResponse(responseCode = "404", description = "User is not found")
    })
    @PutMapping(path = "/im/v1/users/.change-identifier", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> changeIdentifier(@RequestBody ChangeIdentifierRequest request) {
        return ResponseEntity.ok(identityManagementUserService.changePersonIdentifier(request));
    }

}
