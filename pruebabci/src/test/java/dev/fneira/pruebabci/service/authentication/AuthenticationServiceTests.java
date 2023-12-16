package dev.fneira.pruebabci.service.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.fneira.pruebabci.client.user.UserClient;
import dev.fneira.pruebabci.client.user.UserClientResponse;
import dev.fneira.pruebabci.dto.AuthResponseDto;
import dev.fneira.pruebabci.dto.SignInRequest;
import dev.fneira.pruebabci.service.authentication.AuthenticationServiceImpl;
import dev.fneira.pruebabci.service.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationServiceTests {

  @Mock private UserClient userClient;

  @Mock private JwtService jwtService;

  @Mock private AuthenticationManager authenticationManager;

  @InjectMocks private AuthenticationServiceImpl authenticationService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void signIn_OK() {
    when(userClient.signIn(any()))
        .thenReturn(
            UserClientResponse.builder()
                .id(1)
                .email("fe.neiraj@gmail.com")
                .password("123456")
                .build());

    when(jwtService.generateToken(any())).thenReturn("token");

    final AuthResponseDto result =
        authenticationService.signin(
            SignInRequest.builder().email("fe.neiraj@gmail.com").password("123456").build());

    assertNotNull(result.getToken());
    assertEquals("token", result.getToken());
  }

  @Test()
  void signIn_NOK_credentialsInvalid() {
    when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("") {});

    try{
      authenticationService.signin(
              SignInRequest.builder().email("fe.neiraj@gmail.com").password("123456").build());
    }catch (Exception e){
        assertInstanceOf(AuthenticationException.class, e);
    }
  }
}
