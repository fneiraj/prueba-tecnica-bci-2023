package dev.fneira.pruebabci.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Servicio de usuarios
 */
public interface UserService {

  /**
   * Obtiene el servicio de detalles de usuario
   * @return
   */
  UserDetailsService userDetailsService();
}
