package dev.fneira.pruebabci.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datos de la petición de inicio de sesión
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignInRequest {

  private String email;
  private String password;
}
