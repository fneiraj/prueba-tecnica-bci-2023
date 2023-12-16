package dev.fneira.pruebabci.service.offers;

import dev.fneira.pruebabci.client.offers.OfferClient;
import dev.fneira.pruebabci.client.offers.TakeOfferClientRequest;
import dev.fneira.pruebabci.client.offers.TakeOfferClientResponse;
import dev.fneira.pruebabci.client.paymeans.PaymeansClientResponse;
import dev.fneira.pruebabci.constant.Importance;
import dev.fneira.pruebabci.constant.Urgency;
import dev.fneira.pruebabci.dto.GenericResponseDto;
import dev.fneira.pruebabci.dto.OfferDto;
import dev.fneira.pruebabci.dto.RequestOfertaRequestDto;
import dev.fneira.pruebabci.exception.GenericException;
import dev.fneira.pruebabci.mapper.OfferMapper;
import dev.fneira.pruebabci.service.paymean.PaymeanService;
import dev.fneira.pruebabci.service.report.ReportService;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/** Implementación del servicio de ofertas */
@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {

  /** Cliente de ofertas */
  private final OfferClient offerClient;

  /** Servicio de reportes */
  private final ReportService reportService;

  /** Servicio de medios de pago */
  private final PaymeanService paymeanService;

  /**
   * Obtiene las ofertas según los filtros
   *
   * @param importance
   * @param urgent
   * @return
   */
  @Override
  public List<OfferDto> getOffers(final Importance importance, final Urgency urgent) {
    final String importanceQry =
        Optional.ofNullable(importance).map(Importance::getApiValue).orElse("ALL");
    final String urgencyQry = Optional.ofNullable(urgent).map(Urgency::getApiValue).orElse("ALL");

    return offerClient
        .getOffersByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName(),
            importanceQry,
            urgencyQry)
        .getOffers()
        .stream()
        .map(OfferMapper::toDto)
        .collect(Collectors.toList());
  }

  /**
   * Obtiene las ofertas según los filtros
   *
   * @param importance
   * @param urgent
   * @param category
   * @return
   */
  @Override
  public List<OfferDto> getOffers(
      final Importance importance, final Urgency urgent, final String category) {
    return this.getOffers(importance, urgent).stream()
        .filter(offerDto -> offerDto.getCategory().equalsIgnoreCase(category))
        .collect(Collectors.toList());
  }

  /**
   * Toma la oferta
   *
   * @param requestOfertaRequestDto
   * @return
   */
  @Retry(name = "takeOffer", fallbackMethod = "getTakeOfferFallback")
  @Override
  public GenericResponseDto takeOffer(final RequestOfertaRequestDto requestOfertaRequestDto) {
    final PaymeansClientResponse.Paymean paymentFound =
        paymeanService.getPaymentMeans(requestOfertaRequestDto.getIdMedioPago());

    final OfferDto offerDto = getOffers(requestOfertaRequestDto);

    final TakeOfferClientRequest takeOfferClientRequest =
        TakeOfferClientRequest.builder()
            .paymean(paymentFound.getName())
            .offerId(offerDto.getName())
            .build();

    final TakeOfferClientResponse response = offerClient.takeOffer(takeOfferClientRequest);

    return GenericResponseDto.builder().message(response.getDetail()).build();
  }

  private OfferDto getOffers(final RequestOfertaRequestDto requestOfertaRequestDto) {
    return this.getOffers(null, null).stream()
        .filter(offerDto -> offerDto.getId() == requestOfertaRequestDto.getIdOferta())
        .findFirst()
        .orElseThrow(
            () ->
                new GenericException(
                    String.format(
                        "La oferta n° %s no existe", requestOfertaRequestDto.getIdOferta())));
  }

  /**
   * Fallback de la toma de oferta
   *
   * @param requestOfertaRequestDto
   * @param e
   * @return
   */
  public GenericResponseDto getTakeOfferFallback(
      final RequestOfertaRequestDto requestOfertaRequestDto, final Exception e) {
    /**
     * En este punto se puede consumir un servicio de splunk para reportar el error o ejecutar cualquier accion
     * que se desee. para posteriormente tomar decisiones sobre el comportamiento del sistema.
     */
    this.reportService.addFailure(
        e,
        "take-offer",
        SecurityContextHolder.getContext().getAuthentication().getName(),
        requestOfertaRequestDto);

    if (e instanceof GenericException) {
      throw (GenericException) e;
    }

    return GenericResponseDto.builder()
        .message("Error al tomar oferta, favor reintentar mas tarde.")
        .build();
  }
}
