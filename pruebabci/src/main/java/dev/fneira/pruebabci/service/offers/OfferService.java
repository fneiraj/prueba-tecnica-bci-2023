package dev.fneira.pruebabci.service.offers;

import dev.fneira.pruebabci.constant.Importance;
import dev.fneira.pruebabci.constant.Urgency;
import dev.fneira.pruebabci.dto.GenericResponseDto;
import dev.fneira.pruebabci.dto.OfferDto;
import dev.fneira.pruebabci.dto.RequestOfertaRequestDto;
import java.util.List;

/**
 * Servicio de ofertas
 */
public interface OfferService {
  /**
   * Obtiene las ofertas
   * @param importance
   * @param urgent
   * @return
   */
  List<OfferDto> getOffers(final Importance importance, final Urgency urgent);

  /**
   * Obtiene las ofertas
   * @param importance
   * @param urgent
   * @param category
   * @return
   */
  List<OfferDto> getOffers(
      final Importance importance, final Urgency urgent, final String category);

  /**
   * Obtiene las ofertas
   * @param requestOfertaRequestDto
   * @return
   */
  GenericResponseDto takeOffer(final RequestOfertaRequestDto requestOfertaRequestDto);
}
