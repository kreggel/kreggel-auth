package de.kreggel.auth.service.api;

import com.google.common.base.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Version {

    private static final String BUILD_PROPERTIES = "build.properties";

    private static final String PREFIX = "git.";
    private static final String BUILD_VERSION = PREFIX + "build.version";
    private static final String BUILD_TIMESTAMP = PREFIX + "build.time";
    private static final String BUILD_NUMBER = PREFIX + "commit.id.abbrev";

    private static Properties properties = new Properties();

    private Version() {
    }

    static {
        try (InputStream inputStream = Version.class.getClassLoader().getResourceAsStream(BUILD_PROPERTIES)) {
            if (inputStream == null) {
                throw new IllegalStateException("can't locate build information file on classpath. file: " + BUILD_PROPERTIES);
            }
            properties.load(inputStream);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Returns the application version.
     *
     * @return the application version.
     */
    public static String getVersion() {
        return getRequiredProperty(BUILD_VERSION);
    }

    /**
     * Returns the build timestamp in ISO-8601 UTC format.
     *
     * @return the build timestamp.
     */
    public static String getBuildTimestamp() {
        return getRequiredProperty(BUILD_TIMESTAMP);
    }

    /**
     * Returns the source code management revision this application was built from.
     *
     * @return the scm revision.
     */
    public static String getScmRevision() {
        return getRequiredProperty(BUILD_NUMBER);
    }

    /*
     * privates below.
     */

    private static String getRequiredProperty(String property) {
        String value = properties.getProperty(property);
        if (Strings.isNullOrEmpty(value)) {
            throw new IllegalStateException("can't find required property in build information file. file: " + BUILD_PROPERTIES + " and property: " + property);
        }
        return value;
    }
}