package dev.fneira.pruebabci.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Clase de configuración para Swagger */
@Configuration
public class SwaggerConfig {

  // Se define la información para Swagger
  @Bean
  public OpenAPI openApiInformation() {
    return new OpenAPI()
        .info(
            new Info()
                .contact(new Contact().email("hola@fneira.dev").name("Fernando Neira"))
                .description("Proyecto de prueba tecnica para BCI")
                .title("Prueba Tecnica BCI - Fernando Neira")
                .version("V1.0.0"))
        .addServersItem(new Server().url("http://localhost:8080").description("Local Server"));
  }
}
