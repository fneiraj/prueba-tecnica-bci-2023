package dev.fneira.pruebabci.controller.advice;

import dev.fneira.pruebabci.dto.GenericResponseDto;
import dev.fneira.pruebabci.exception.GenericException;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Clase que maneja las excepciones de la aplicación */
@RestControllerAdvice
@Slf4j
public class ErrorControllerAdvice extends ResponseEntityExceptionHandler {
  /**
   * Método que maneja las excepciones de validación de los parámetros de entrada
   *
   * @param exception Excepción
   * @param headers Cabeceras
   * @param status Estado
   * @param request Petición
   * @return Respuesta
   */
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    logError(exception);
    List<String> details = new ArrayList<>();
    exception.getBindingResult().getAllErrors().forEach(ex -> details.add(ex.getDefaultMessage()));
    return new ResponseEntity<>(
        GenericResponseDto.builder()
            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .details(details)
            .build(),
        HttpStatus.BAD_REQUEST);
  }

  /**
   * Método que maneja las excepciones de parámetros de entrada faltantes
   *
   * @param exception Excepción
   * @param headers Cabeceras
   * @param status Estado
   * @param request Petición
   * @return Respuesta
   */
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    String detail = String.format("Parameter %s is missing", exception.getParameterName());
    GenericResponseDto error =
        GenericResponseDto.builder()
            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .details(Collections.singletonList(detail))
            .build();
    return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Método que maneja las excepciones de métodos no soportados
   *
   * @param exception Excepción
   * @param headers Cabeceras
   * @param status Estado
   * @param request Petición
   * @return Respuesta
   */
  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    StringBuilder detail = new StringBuilder();
    detail.append(exception.getMethod()).append("Method %s is not supported");
    Objects.requireNonNull(exception.getSupportedHttpMethods())
        .forEach(httpMethod -> detail.append(httpMethod).append(" "));
    GenericResponseDto error =
        GenericResponseDto.builder()
            .message(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase())
            .details(Collections.singletonList(detail.toString().trim()))
            .build();
    return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * Método que maneja las excepciones de tipos de medios no soportados
   *
   * @param exception Excepción
   * @param headers Cabeceras
   * @param status Estado
   * @param request Petición
   * @return Respuesta
   */
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    StringBuilder detail = new StringBuilder();
    detail.append(exception.getContentType()).append("media_type % is not supported");
    exception.getSupportedMediaTypes().forEach(mediaType -> detail.append(mediaType).append(", "));
    GenericResponseDto error =
        GenericResponseDto.builder()
            .message(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase())
            .details(Collections.singletonList(detail.toString()))
            .build();
    return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
  }

  /**
   * Método que maneja las excepciones de la aplicación
   *
   * @param exception Excepción
   * @return Respuesta
   */
  @ExceptionHandler(GenericException.class)
  public ResponseEntity<Object> handleErrorException(GenericException exception) {
    logError(exception);
    GenericResponseDto error =
        GenericResponseDto.builder()
            .message(exception.getMessage())
            .details(exception.getDetails())
            .build();
    return new ResponseEntity<>(error, exception.getHttpStatus());
  }

  /**
   * Método que maneja las excepciones de rutas no encontradas
   *
   * @param exception Excepción
   * @param headers Cabeceras
   * @param status Estado
   * @param request Petición
   * @return Respuesta
   */
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException exception,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    logError(exception);
    GenericResponseDto error =
        GenericResponseDto.builder()
            .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .build();
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Método que maneja las excepciones de conversión de tipos
   *
   * @param ex Excepción
   * @return Respuesta
   */
  @ExceptionHandler(ConversionFailedException.class)
  public ResponseEntity<String> handleConflict(RuntimeException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Método que maneja las excepciones de credenciales inválidas
   *
   * @param exception Excepción
   * @return Respuesta
   */
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException exception) {
    logError(exception);
    GenericResponseDto error = GenericResponseDto.builder().message(exception.getMessage()).build();
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  /**
   * Método que maneja las excepciones de la aplicación
   *
   * @param exception Excepción
   * @return Respuesta
   */
  @ExceptionHandler(ExpiredJwtException.class)
  public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException exception) {
    logError(exception);
    GenericResponseDto error = GenericResponseDto.builder().message("sesión expirada").build();
    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleAllExceptions(Exception exception) {
    logError(exception);
    GenericResponseDto error =
        GenericResponseDto.builder()
            .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
            .build();
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Metodo que realiza trace de la excepcion
   *
   * @param exception Excepcion
   */
  private void logError(Exception exception) {
    log.error("Exception handle", exception);
  }
}
