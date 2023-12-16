package dev.fneira.pruebabci.client.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.fneira.pruebabci.exception.GenericException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;

/**
 * Esta clase se encarga de interceptar las excepciones que se producen en las llamadas a los
 * servicios externos. En caso de que la respuesta del servicio externo sea un error, se parsea el
 * mensaje de error y se lanza una excepción propia de la aplicación.
 */
@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

  private static final ErrorDecoder delegate;

  private static final ObjectMapper mapper;

  static {
    delegate = new ErrorDecoder.Default();
    mapper = new ObjectMapper();
  }

  @Override
  public Exception decode(final String methodKey, Response response) {

    log.trace("Una excepción fue capturada en {}, intentando parsear el payload.", methodKey);

    if (response.body() == null) {
      log.error("Error parseando payload.");
      return delegate.decode(methodKey, response);
    }

    final String message;

    try {
      message = mapper.readValue(response.body().asInputStream(), String.class);
    } catch (IOException e) {
      log.trace("Error parseando mensaje.", e);
      return delegate.decode(methodKey, response);
    }

    final HttpStatus status = HttpStatus.valueOf(response.status());

    final String firstMessage = message.isEmpty() ? status.getReasonPhrase() : message;

    log.trace("Lanzando excepcion con el mensaje:  \"{}\" ", firstMessage);

    if (status == HttpStatus.FORBIDDEN || status == HttpStatus.UNAUTHORIZED) {
      return new AccessDeniedException(firstMessage);
    } else if (status.is4xxClientError()) {
      return new GenericException(status.getReasonPhrase(), Collections.singletonList(message));

    } else {
      return new RestClientException(firstMessage);
    }
  }
}
