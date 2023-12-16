package dev.fneira.pruebabci.service.authentication;

import dev.fneira.pruebabci.dto.AuthResponseDto;
import dev.fneira.pruebabci.dto.SignInRequest;

/**
 * Servicio de autenticación
 */
public interface AuthenticationService {

  /**
   * Autenticación de usuario
   * @param request datos de autenticación
   * @return token de autenticación
   */
  AuthResponseDto signin(SignInRequest request);
}
