package dev.fneira.pruebabci.constant;

import dev.fneira.pruebabci.exception.ParamException;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enumerado para los tipos de importancia
 */
@Getter
public enum Importance {
  MUY_BAJA("muy_baja", "VERY_LOW"),
  BAJA("baja", "LOW"),
  MEDIA("media", "MEDIUM"),
  ALTA("alta", "HIGH"),
  MUY_ALTA("muy_alta", "VERY_HIGH");

  /**
   * Valor de importancia para el request del ms
   */
  private final String requestValue;

  /**
   * Valor de importancia para el request del api
   */
  private final String apiValue;

  Importance(final String requestValue, String apiValue) {
    this.requestValue = requestValue;
    this.apiValue = apiValue;
  }

  public static Importance getByImportance(final String findStr) {
    return Arrays.stream(Importance.values())
        .filter(importance -> importance.getRequestValue().equalsIgnoreCase(findStr))
        .findFirst()
        .orElseThrow(
            () ->
                new ParamException(
                    String.format(
                        "Importancia %s no valida, opciones validas: muy_baja, baja, media, alta y muy_alta.",
                        findStr)));
  }
}
