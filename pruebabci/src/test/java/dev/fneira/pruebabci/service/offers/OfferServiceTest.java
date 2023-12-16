package dev.fneira.pruebabci.service.offers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import dev.fneira.pruebabci.client.offers.OfferClient;
import dev.fneira.pruebabci.client.offers.OfferClientResponse;
import dev.fneira.pruebabci.client.offers.TakeOfferClientResponse;
import dev.fneira.pruebabci.client.paymeans.PaymeansClientResponse;
import dev.fneira.pruebabci.constant.Importance;
import dev.fneira.pruebabci.constant.Urgency;
import dev.fneira.pruebabci.dto.GenericResponseDto;
import dev.fneira.pruebabci.dto.OfferDto;
import dev.fneira.pruebabci.dto.RequestOfertaRequestDto;
import dev.fneira.pruebabci.models.User;
import dev.fneira.pruebabci.service.paymean.PaymeanService;
import dev.fneira.pruebabci.service.report.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

public class OfferServiceTest {
  @Mock private OfferClient offerClient;

  @Mock private ReportService reportService;

  @Mock private PaymeanService paymeanService;

  @InjectMocks private OfferServiceImpl offerService;

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
    when(offerClient.getOffersByEmail(anyString(), anyString(), anyString()))
        .thenReturn(getOffersMock());

    final List<OfferDto> result =
        offerService.getOffers(Importance.ALTA, Urgency.BAJA, "categoria");

    assertNotNull(result);
    assertFalse(result.isEmpty());
  }

  @Test
  void takeOffer_OK() {
    when(offerClient.getOffersByEmail(anyString(), anyString(), anyString()))
        .thenReturn(getOffersMock());

    when(paymeanService.getPaymentMeans(anyInt()))
        .thenReturn(PaymeansClientResponse.Paymean.builder().id(1).build());

    when(offerClient.takeOffer(any()))
        .thenReturn(
            TakeOfferClientResponse.builder()
                .status("ok")
                .detail("Oferta tomada correctamente")
                .build());
    final GenericResponseDto result =
        offerService.takeOffer(
            RequestOfertaRequestDto.builder().idMedioPago(1).idOferta(1).build());

    assertNotNull(result);
  }

  private OfferClientResponse getOffersMock() {
    return OfferClientResponse.builder()
        .offers(
            Collections.singletonList(
                OfferClientResponse.Offer.builder()
                    .id(1)
                    .name("Oferta 1")
                    .description("Descripcion oferta 1")
                    .importance(Importance.ALTA.getRequestValue())
                    .urgent(Urgency.MEDIA.getRequestValue())
                    .price("1000")
                    .discount("10")
                    .category("categoria")
                    .build()))
        .build();
  }
}
