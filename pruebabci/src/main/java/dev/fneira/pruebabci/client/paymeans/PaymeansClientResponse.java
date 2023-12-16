package dev.fneira.pruebabci.client.paymeans;

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
public class PaymeansClientResponse {
  private List<Paymean> paymeans;

  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  public static class Paymean {

    private int id;
    private String name;
    private String type;
    private String number;
    private String balance;
  }
}
