package pl.edu.uj.tcs.quizhub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    // endpoint for swagger: /swagger-ui/index.html

    @Bean
    public OpenAPI baseOpenApi() {
        return new OpenAPI().info(new Info().title("QuizHub - Swagger Docs").version("1.1.0").description("That's the documentation for our QuizHub API"));
    }
}
