package de.kreggel.auth.core.configuration.internal;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.google.common.base.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * This class can be used to read properties from the filesystem. In addition to the normal {@link Properties} it allows
 * the following features: <br/>
 * <br/>
 * <ul>
 * <li>Lookup and merge several properties into one</li>
 * <li>Resolve placeholders like ${myKey} within the properties</li>
 * <li>Hierarchical overrides of properties in the order: properties, last specified properties, system properties,
 * system env</li>
 * </ul>
 * Currently it loads the specified properties in the order given. Last loaded properties overwrite existing ones with
 * the same key. A specified System property overwrites properties. A System ENV variable will always overwrite
 * everything except when it set on System property level. It also does not complain about non-existing properties files
 * but does complain about non resolvable placeholders.
 */
public class Configuration {

    private ExposingPropertySourcesPlaceholderConfigurer springPropertyConfigurer;

    /**
     * Creates a configuration instance reading the given properties files. Order is important since last specified
     * properties will overwrite first ones. The properties are searched on the classpath.
     *
     * @param configFiles
     *            a list of {@link Properties} filenames to load.
     */
    public Configuration(String... configFiles) {

        if (configFiles == null || configFiles.length == 0) {
            throw new IllegalArgumentException("Parameter 'configFiles' must not be null or empty.");
        }

        springPropertyConfigurer = new ExposingPropertySourcesPlaceholderConfigurer();

        List<Resource> resources = new ArrayList<>();
        for (String configFile : configFiles) {
            if (configFile == null) {
                throw new IllegalArgumentException("a configfile name must not be null");
            }
            Resource resource = new ClassPathResource(configFile);
            resources.add(resource);
        }

        springPropertyConfigurer.setLocations(resources.toArray(new Resource[resources.size()]));
        springPropertyConfigurer.setIgnoreResourceNotFound(true);
        springPropertyConfigurer.setEnvironment(new StandardEnvironment());
        springPropertyConfigurer.postProcessBeanFactory(new DefaultListableBeanFactory());
    }

    /**
     * Gets all properties where their key starts with the given prefix. Also it can be specified, whether the prefix
     * should be removed from the key when returning the map.<br/>
     * <br/>
     * Note: only properties from the specified files will be matched. No system or env properties!
     *
     * @param prefix
     *            the prefix to match the keys with.
     * @param dropPrefixFromKey
     *            true, to remove the prefix from the key in the resulting map, otherwise false.
     * @return the map containing the matched properties.
     */
    public Map<String, String> getByPrefix(String prefix, boolean dropPrefixFromKey) {

        Map<String, String> matchedProperties = new HashMap<>();

        Properties properties = springPropertyConfigurer.getLocalProperties();

        for (Object object : properties.keySet()) {
            String key = object.toString();
            if (key.startsWith(prefix)) {
                String value = get(key);
                if (dropPrefixFromKey) {
                    key = key.substring(prefix.length());
                }
                matchedProperties.put(key, value);
            }
        }

        return matchedProperties;
    }

    /**
     * Gets the required property value for the given name.
     *
     * @param name
     *            the name of the property to get.
     * @return the value.
     * @throws IllegalStateException
     *             when the value is null or empty.
     */
    public String get(String name) {

        if (name == null) {
            throw new IllegalArgumentException("Parameter 'name' must not be null.");
        }

        String value = springPropertyConfigurer.getProperty(name);

        if (!Strings.isNullOrEmpty(value)) {
            return value;
        }

        throw new IllegalStateException("configuration key '" + name + "' must not be null or empty");
    }

    /**
     * Gets the property value for the given name, returning the defaultValue when null or empty.
     *
     * @param name
     *            the name of the property to get.
     * @param defaultValue
     *            the default value to use when the value is null or empty.
     * @return the value for the property name, or the default value when null or empty.
     */
    public String get(String name, String defaultValue) {

        if (name == null) {
            throw new IllegalArgumentException("Parameter 'name' must not be null.");
        }

        return springPropertyConfigurer.getProperty(name, defaultValue);
    }

    /**
     * Gets the required property value for the given name as boolean.
     *
     * @param name
     *            the name of the property value to get.
     * @return the value as boolean.
     * @throws IllegalStateException
     *             when the value is null or empty.
     */
    public boolean getBoolean(String name) {
        return Boolean.parseBoolean(get(name));
    }

    /**
     * Gets the required property value for the given name as int.
     *
     * @param name
     *            the name of the property value to get.
     * @return the value as int.
     * @throws IllegalStateException
     *             when the value is null or empty.
     */
    public int getInteger(String name) {
        return Integer.parseInt(get(name));
    }

    /**
     * Gets the required property value for the given name as long.
     *
     * @param name
     *            the name of the property value to get.
     * @return the value as long.
     * @throws IllegalStateException
     *             when the value is null or empty.
     */
    public long getLong(String name) {
        return Long.parseLong(get(name));
    }

    /**
     * Little helper to expose resolved properties from {@link PropertySourcesPlaceholderConfigurer} that is usually
     * only used for resolving placeholders within the applicationContext.xml. We want to use the same functionality but
     * with property files only - independent of any app context.
     *
     * @author vguna
     */
    private class ExposingPropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

        private ConfigurablePropertyResolver properties;

        @Override
        protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver)
                throws BeansException {

            properties = propertyResolver;

            super.processProperties(beanFactoryToProcess, propertyResolver);
        }

        public String getProperty(String key, String defaultValue) {
            return properties.getProperty(key, defaultValue);
        }

        public String getProperty(String key) {
            return properties.getProperty(key);
        }

        public Properties getLocalProperties() {
            return (Properties) getAppliedPropertySources().get(LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME).getSource();
        }
    }
}
