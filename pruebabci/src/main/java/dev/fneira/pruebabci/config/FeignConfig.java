package dev.fneira.pruebabci.config;

import dev.fneira.pruebabci.client.interceptor.CustomFeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Clase de configuración para Feign */
@Configuration
public class FeignConfig {

  /**
   * Se crea un bean de tipo CustomFeignErrorDecoder para manejar los errores de Feign
   * @return CustomFeignErrorDecoder
   */
  @Bean
  public CustomFeignErrorDecoder customErrorDecoder() {
    return new CustomFeignErrorDecoder();
  }
}
