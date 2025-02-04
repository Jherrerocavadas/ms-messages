package br.com.jherrerocavadas.msmessages.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${application.name:}")
    private String applicationName;

    @Value("${application.description:}")
    private String applicationDescription;

    @Value("${application.version:}")
    private String applicationVersion;

    private final String API_TOKEN_HEADER = "X-API-TOKEN";

    @Bean
    public OpenAPI apiConfig() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_TOKEN_HEADER,
                                new SecurityScheme()
                                        .in(SecurityScheme.In.HEADER)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .name(API_TOKEN_HEADER)
                ))
                .security(List.of(new SecurityRequirement().addList(API_TOKEN_HEADER)))
                .info(new Info().title(applicationName)
                        .description(new String(applicationDescription.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8))
                        .version(applicationVersion)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Jherrerocavadas Github")
                        .url("https://github.com/JHerrerocavadas"));
    }
}
