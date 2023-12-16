package dev.fneira.pruebabci.service.user;

import dev.fneira.pruebabci.client.user.UserClient;
import dev.fneira.pruebabci.mapper.UserMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación de servicio de usuarios
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  /**
   * Cliente de usuarios
   */
  private final UserClient userClient;

  /**
   * Obtiene el servicio de detalles de usuario
   * @return
   */
  @Override
  public UserDetailsService userDetailsService() {
    return email ->
        Optional.ofNullable(userClient.getUserByEmail(email))
            .map(UserMapper::toUser)
            .orElseThrow(
                () -> new UsernameNotFoundException(String.format("User %s not found", email)));
  }
}
