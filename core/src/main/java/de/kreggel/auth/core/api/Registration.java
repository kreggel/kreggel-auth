package de.kreggel.auth.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Registration {
    public static void main(String[] args) {
        final Logger logger = LoggerFactory.getLogger(Registration.class);
        logger.debug("--- starting up ---");
        logger.info(Version.getVersion());
    }
}