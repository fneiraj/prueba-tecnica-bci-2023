package dev.fneira.pruebabci.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Datos de la oferta
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OfferDto {

  private int id;
  private String name;
  private String description;
  private String category;
  private String importance;
  private String urgent;
  private String duration;
  private String startDate;
  private String endDate;
  private String image;
  private String price;
  private String discount;
  private String discountPrice;
  private String discountPercentage;
}
