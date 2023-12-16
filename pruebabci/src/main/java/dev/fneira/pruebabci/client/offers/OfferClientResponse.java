package dev.fneira.pruebabci.client.offers;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferClientResponse {

  private List<Offer> offers;

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  public static class Offer {

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
}
