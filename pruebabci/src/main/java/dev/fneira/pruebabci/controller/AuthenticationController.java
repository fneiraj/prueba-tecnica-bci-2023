package dev.fneira.pruebabci.controller;

import dev.fneira.pruebabci.dto.AuthResponseDto;
import dev.fneira.pruebabci.dto.SignInRequest;
import dev.fneira.pruebabci.service.authentication.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para la autenticación
 */
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  /**
   * Iniciar sesión
   * @param request Datos de la solicitud
   * @return Datos de la respuesta
   */
  @PostMapping("/iniciar-sesion")
  public ResponseEntity<AuthResponseDto> signin(@RequestBody SignInRequest request) {
    return ResponseEntity.ok(authenticationService.signin(request));
  }
}
