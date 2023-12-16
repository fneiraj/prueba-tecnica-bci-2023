package dev.fneira.pruebabci.client.offers;

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
public class TakeOfferClientResponse {

  private String status;
  private String message;
  private String detail;
}
