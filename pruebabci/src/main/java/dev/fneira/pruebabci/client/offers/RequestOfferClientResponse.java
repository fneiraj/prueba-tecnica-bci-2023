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
public class RequestOfferClientResponse {

  private Integer id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
