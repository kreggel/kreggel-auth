package de.kreggel.auth.core.configuration;

import de.kreggel.auth.core.configuration.internal.Configuration;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
@Singleton
public class AuthConfig extends Configuration {

    private static final String CONFIG_FILE = "config.properties";

    private static final AuthConfig SINGLETON = new AuthConfig();

    public AuthConfig() {
        super(CONFIG_FILE);
    }

    /**
     * Gets the singleton instance of the {@link AuthConfig} where CDI isn't available.
     *
     * @return the singleton {@link AuthConfig} instance.
     */
    public static AuthConfig getInstance() {
        return SINGLETON;
    }
}
