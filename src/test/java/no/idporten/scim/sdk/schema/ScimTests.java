package no.idporten.scim.sdk.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import no.idporten.scim.app.IDPortenUser;
import no.idporten.scim.sdk.ScimResourceHandler;
import no.idporten.scim.sdk.ScimResource;
import no.idporten.scim.web.ScimController;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ScimTests {

    @Test
    public void testParseResource() throws Exception {
        // Oppsett, lesing av scim schemas
        ScimSchemaRegistry schemaRegistry = new ScimSchemaRegistry();
        schemaRegistry.init();
        ScimResourceHandler scimResourceHandler = new ScimResourceHandler(schemaRegistry);

        // input scim resource - i eksempelet fra en fil, til vanlig via HTTP
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")); // TODO ser ikke ut til å virke
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ScimResource userResource = objectMapper.readValue(new DefaultResourceLoader().getResource("classpath:/data/User.json").getFile(), ScimResource.class);

        // valider input mot skjema
        scimResourceHandler.validate(userResource);
        System.out.println("userResource = " + userResource);

        // konverter input til intern modell
        IDPortenUser idPortenUser = scimResourceHandler.convert(userResource, IDPortenUser.class);
        assertEquals("12829499914", idPortenUser.getPid());
        assertTrue(idPortenUser.getEjournalReferanse().containsAll(Arrays.asList("12345", "67890")));
        idPortenUser.setId(UUID.randomUUID().toString());
        idPortenUser.setIdportenCreatedDate(ZonedDateTime.now().minusDays(14));
        idPortenUser.setIdportenLastUpdatedDate(ZonedDateTime.now());
        idPortenUser.setClosedCode("ID-TYVERI");
        idPortenUser.setClosedDate(ZonedDateTime.now());

        // konverter til ekstern modell
        ScimResource convertedResource = scimResourceHandler.convert(idPortenUser, userResource.getSchemas());

        // Serialiser som JSON
        System.out.println(objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(convertedResource));
    }

    @Test
    public void testControllerService() throws Exception {
        // Oppsett, lesing av scim schemas
        ScimSchemaRegistry schemaRegistry = new ScimSchemaRegistry();
        schemaRegistry.init();
        ScimResourceHandler scimResourceHandler = new ScimResourceHandler(schemaRegistry);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")); // TODO ser ikke ut til å virke
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ScimResource userResource = objectMapper.readValue(new DefaultResourceLoader().getResource("classpath:/data/User.json").getFile(), ScimResource.class);

        ScimController scimController = new ScimController(scimResourceHandler);
        ScimResource response = scimController.post(userResource);
        System.out.println(response);
        Object retrieved = scimController.get(response.getId());
        System.out.println(retrieved);





    }

}
