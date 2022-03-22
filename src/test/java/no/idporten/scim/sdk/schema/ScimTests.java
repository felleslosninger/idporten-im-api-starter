package no.idporten.scim.sdk.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.idporten.scim.app.IDPortenUser;
import no.idporten.scim.sdk.ScimResourceHandler;
import no.idporten.scim.sdk.ScimResource;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScimTests {

    @Test
    public void testParseResource() throws Exception {
        // Oppsett, lesing av scim schemas
        ScimSchemaRegistry schemaRegistry = new ScimSchemaRegistry();
        schemaRegistry.init();
        ScimResourceHandler scimResourceHandler = new ScimResourceHandler(schemaRegistry);

        // input scim resource - i eksempelet fra en fil, til vanlig via HTTP
        ScimResource userResource = new ObjectMapper().readValue(new DefaultResourceLoader().getResource("classpath:/data/User.json").getFile(), ScimResource.class);

        // valider input mot skjema
        scimResourceHandler.validate(userResource);

        // konverter input til intern modell
        IDPortenUser idPortenUser = scimResourceHandler.convert(userResource, IDPortenUser.class);
        assertEquals("12829499914", idPortenUser.getPid());
        assertEquals("idporten!", idPortenUser.getExtra());

        // konverter til ekstern modell
        ScimResource convertedResource = scimResourceHandler.convert(idPortenUser, userResource.getSchemas());

        // Serialiser som JSON
        System.out.println(new ObjectMapper().writer().writeValueAsString(convertedResource));
    }

}
