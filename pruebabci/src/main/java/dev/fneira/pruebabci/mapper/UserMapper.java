package dev.fneira.pruebabci.mapper;

import dev.fneira.pruebabci.client.user.UserClientResponse;
import dev.fneira.pruebabci.models.User;

/**
 * Clase que mapea los datos de la respuesta del cliente a un objeto de tipo User
 */
public class UserMapper {

  private UserMapper() {}

  public static User toUser(UserClientResponse userClientResponse) {
    return User.builder()
        .id(userClientResponse.getId())
        .firstName(userClientResponse.getFirstName())
        .lastName(userClientResponse.getLastName())
        .email(userClientResponse.getEmail())
        .password(userClientResponse.getPassword())
        .build();
  }
}
