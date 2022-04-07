package no.idporten.scim.config;

import lombok.extern.slf4j.Slf4j;
import no.idporten.scim.spi.ScimUserService;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ComponentScan(basePackages = {"no.idporten.scim.api"})
public class ScimAutoConfiguration {

    public ScimAutoConfiguration() {
        log.info("Autoconfigure with {} started", this.getClass().getName());
    }

    @ConditionalOnBean(type = "no.idporten.scim.spi.ScimUserService")
    @Bean
    public ScimUserService scimUserServiceType(ScimUserService scimUserService) {
        log.info("Using user service implementation {}", scimUserService.getClass().getName());
        return scimUserService;
    }

}
