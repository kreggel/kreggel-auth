package de.kreggel.auth.service.bootstrap;

import de.kreggel.auth.core.configuration.AuthConfig;
import de.kreggel.auth.service.status.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

@Singleton
@Configuration
public class WebConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(WebConfiguration.class);

    @Bean
    public AuthConfig authConfig() {
        return new AuthConfig();
    }

    @Bean
    public Version version() {
        return Version.fromBuildInfo();
    }

    @PostConstruct
    public void initialize() {
        System.out.println("WebConfiguration.initialized");
    }
}
