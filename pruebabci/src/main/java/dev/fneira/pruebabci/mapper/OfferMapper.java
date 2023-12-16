package dev.fneira.pruebabci.mapper;

import dev.fneira.pruebabci.client.offers.OfferClientResponse;
import dev.fneira.pruebabci.dto.OfferDto;

/**
 * Clase que mapea la respuesta del servicio de ofertas a un DTO
 */
public class OfferMapper {

  private OfferMapper() {}

  public static OfferDto toDto(OfferClientResponse.Offer offer) {
    return OfferDto.builder()
        .id(offer.getId())
        .name(offer.getName())
        .description(offer.getDescription())
        .category(offer.getCategory())
        .importance(offer.getImportance())
        .urgent(offer.getUrgent())
        .duration(offer.getDuration())
        .startDate(offer.getStartDate())
        .endDate(offer.getEndDate())
        .image(offer.getImage())
        .price(offer.getPrice())
        .discount(offer.getDiscount())
        .discountPrice(offer.getDiscountPrice())
        .discountPercentage(offer.getDiscountPercentage())
        .build();
  }
}
