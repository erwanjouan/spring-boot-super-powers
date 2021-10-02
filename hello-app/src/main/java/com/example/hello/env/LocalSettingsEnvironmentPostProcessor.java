package com.example.hello.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LocalSettingsEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String LOCATION = ".hello/settings";

    private final PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();

    @Override
    public void postProcessEnvironment(final ConfigurableEnvironment configurableEnvironment,
                                       final SpringApplication springApplication) {
        final File file = new File(System.getProperty("user.home"), LOCATION);
        if (file.exists()) {
            final MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            final List<PropertySource<?>> propertySource = this.loadPropertySource(file);
            if (propertySources.contains(
                    CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME)) {
                propertySources.addAfter(
                        CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME,
                        propertySource.get(0));
            } else {
                propertySources.addFirst(propertySource.get(0));
            }
        }
    }

    private List<PropertySource<?>> loadPropertySource(final File f) {
        final FileSystemResource resource = new FileSystemResource(f);
        try {
            return this.loader.load("hello-local", resource);
        } catch (final IOException ex) {
            throw new IllegalStateException("Failed to load local settings from " + f.getAbsolutePath(), ex);
        }
    }

}