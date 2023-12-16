package dev.fneira.pruebabci.constant;

import dev.fneira.pruebabci.exception.ParamException;
import java.util.Arrays;
import lombok.Getter;

/** Enumerado para definir la urgencia de una tarea */
@Getter
public enum Urgency {
  BAJA("baja", "LOW"),
  MEDIA("media", "MEDIUM"),
  ALTA("alta", "HIGH");

  /** Valor que se recibe en el request */
  private final String requestValue;

  /** Valor que se envia en el request al API */
  private final String apiValue;

  Urgency(final String requestValue, String apiValue) {
    this.requestValue = requestValue;
    this.apiValue = apiValue;
  }

  public static Urgency getByImportance(final String urgencyNumber) {
    return Arrays.stream(Urgency.values())
        .filter(urgency -> urgency.getRequestValue().equalsIgnoreCase(urgencyNumber))
        .findFirst()
        .orElseThrow(
            () ->
                new ParamException(
                    String.format(
                        "Urgencia %s no valida, opciones validas: baja, media y alta",
                        urgencyNumber)));
  }
}
