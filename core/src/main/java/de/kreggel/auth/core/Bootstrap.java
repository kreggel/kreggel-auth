package de.kreggel.auth.core;

import de.kreggel.auth.core.api.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrap {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
        logger.debug("--- starting up ---");
        logger.info(Version.getVersion());


    }
}
