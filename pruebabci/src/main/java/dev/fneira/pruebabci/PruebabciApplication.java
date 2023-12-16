package dev.fneira.pruebabci;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication // se define como una aplicación Spring Boot
@EnableFeignClients // habilita el cliente Feign
@EnableAsync // habilita el uso de métodos asincrónicos
public class PruebabciApplication {

  public static void main(String[] args) {
    SpringApplication.run(PruebabciApplication.class, args);
  }
}
