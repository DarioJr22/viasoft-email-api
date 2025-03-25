package com.challangeviasoft.email.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI(
                @Value("${springdoc.openapi.title}") final String title,
                @Value("${springdoc.openapi.description}") final String description,
                @Value("${springdoc.openapi.version}") final String version
        ) {
                return new OpenAPI()
                        .info(new Info()
                                .title(title)
                                .version(version)
                                .description(description));
        }
}
