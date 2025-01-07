package no.stackcanary.javaspringoauth2client.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@OpenAPIDefinition(
        security = @SecurityRequirement(name = "Authorization"),
        info = @Info(title = "StackCanary Oauth2 Client Api", description = "Client service integrating with an Employee resource server",
        contact = @Contact(url = "https://github.com/eiriktve")))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "Authorization", description = "Bearer token",
        in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {}
