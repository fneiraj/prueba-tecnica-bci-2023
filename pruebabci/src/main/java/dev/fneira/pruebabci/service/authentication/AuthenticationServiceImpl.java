package dev.fneira.pruebabci.service.authentication;

import dev.fneira.pruebabci.client.user.UserClient;
import dev.fneira.pruebabci.client.user.UserClientRequest;
import dev.fneira.pruebabci.client.user.UserClientResponse;
import dev.fneira.pruebabci.dto.AuthResponseDto;
import dev.fneira.pruebabci.dto.SignInRequest;
import dev.fneira.pruebabci.mapper.UserMapper;
import dev.fneira.pruebabci.models.User;
import dev.fneira.pruebabci.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/** Implementación del servicio de autenticación */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  /** Cliente de usuarios */
  private final UserClient userClient;

  /** Codificador de contraseñas */
  private final JwtService jwtService;

  /** Administrador de autenticación */
  private final AuthenticationManager authenticationManager;

  /**
   * Autenticación de usuario
   *
   * @param request datos de autenticación
   * @return
   */
  @Override
  public AuthResponseDto signin(SignInRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    final UserClientResponse user =
        userClient.signIn(
            UserClientRequest.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build());
    final String jwt = jwtService.generateToken(UserMapper.toUser(user));
    return AuthResponseDto.builder().token(jwt).build();
  }
}
