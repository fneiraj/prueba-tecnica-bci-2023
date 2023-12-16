package dev.fneira.pruebabci.client.user;

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
public class UserClientRequest {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
}
