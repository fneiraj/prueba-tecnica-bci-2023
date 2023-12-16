package dev.fneira.pruebabci.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * Excepción de parámetro
 */
@Getter
public class ParamException extends RuntimeException {

  private final HttpStatusCode httpStatus;
  private final String param;

  public ParamException(final String param) {
    super(String.format("error in parameter: %s", param));
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    this.param = param;
  }
}
