package io.github.tiagoiwamoto.apppersonalresumebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class ResourceWebConfig implements WebFluxConfigurer {
    final Environment environment;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
        "file:./files"
    };

    public ResourceWebConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
}
