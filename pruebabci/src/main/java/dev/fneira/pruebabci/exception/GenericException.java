package dev.fneira.pruebabci.exception;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * Excepción genérica
 */
@Getter
public class GenericException extends RuntimeException {

  private final HttpStatusCode httpStatus;
  private final List<String> details;

  public GenericException(String message) {
    super(message);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    this.details = Collections.singletonList(message);
  }

  public GenericException(String message, List<String> details) {
    super(message);
    this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    this.details = details;
  }
}
