package dev.fneira.pruebabci.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Servicio de JWT
 */
public interface JwtService {

  /**
   * Extrae el nombre de usuario del token
   * @param token
   * @return
   */
  String extractUserName(String token);

  /**
   * Genera un token para el usuario
   * @param userDetails
   * @return
   */

  String generateToken(UserDetails userDetails);

  /**
   * Valida el token
   * @param token
   * @param userDetails
   * @return
   */

  boolean isTokenValid(String token, UserDetails userDetails);

  boolean isTokenExpired(String token);
}
