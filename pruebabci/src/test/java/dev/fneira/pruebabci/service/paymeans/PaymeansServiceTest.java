package dev.fneira.pruebabci.service.paymeans;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.fneira.pruebabci.client.paymeans.PaymeansClient;
import dev.fneira.pruebabci.client.paymeans.PaymeansClientResponse;
import dev.fneira.pruebabci.exception.GenericException;
import dev.fneira.pruebabci.models.User;
import dev.fneira.pruebabci.service.paymean.PaymeanServiceImpl;
import java.util.Collections;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class PaymeansServiceTest {
  @Mock private PaymeansClient paymeansClient;

  @InjectMocks private PaymeanServiceImpl paymeanService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    final SecurityContext context = SecurityContextHolder.createEmptyContext();

    final User userDetails = mock(User.class);
    when(userDetails.getUsername()).thenReturn("fe.neiraj@gmail.com");

    final UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    final HttpServletRequest request = mock(HttpServletRequest.class);

    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    context.setAuthentication(authToken);
    SecurityContextHolder.setContext(context);
  }

  @Test
  void signIn_OK() {
    when(paymeansClient.getPaymeansByEmail(anyString())).thenReturn(getPaymeans());

    final PaymeansClientResponse.Paymean result = paymeanService.getPaymentMeans(1);

    assertNotNull(result);
  }

  @Test
  void takeOffer_OK() {
    when(paymeansClient.getPaymeansByEmail(anyString()))
        .thenThrow(new GenericException("Error al obtener los medios de pago"));

    try {

      final PaymeansClientResponse.Paymean result = paymeanService.getPaymentMeans(2);
    } catch (Exception e) {
      assertInstanceOf(GenericException.class, e);
      System.out.println(e.getMessage());
    }
  }

  private PaymeansClientResponse getPaymeans() {
    return PaymeansClientResponse.builder()
        .paymeans(
            Collections.singletonList(
                PaymeansClientResponse.Paymean.builder()
                    .id(1)
                    .name("Medio de pago 1")
                    .type("Descripcion medio de pago 1")
                    .build()))
        .build();
  }
}
