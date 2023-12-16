package dev.fneira.pruebabci.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Datos de la respuesta de autenticación
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthResponseDto {

  private String token;
}
