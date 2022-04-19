package no.idporten.im.config;

import lombok.extern.slf4j.Slf4j;
import no.idporten.im.spi.IDPortenIdentityManagementUserService;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Slf4j
@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@ComponentScan(basePackages = {"no.idporten.im.api"})
public class IDPortenIMApiAutoConfiguration {

    public IDPortenIMApiAutoConfiguration() {
        log.info("Autoconfigure with {} started", this.getClass().getName());
    }

    @ConditionalOnBean(type = "no.idporten.im.spi.IDPortenIdentityManagementUserService")
    @Bean
    public IDPortenIdentityManagementUserService identityManagementUserService(IDPortenIdentityManagementUserService identityManagementUserService) {
        log.info("Using user service implementation {}", identityManagementUserService.getClass().getName());
        return identityManagementUserService;
    }

}
