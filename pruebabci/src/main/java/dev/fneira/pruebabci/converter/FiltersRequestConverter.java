package dev.fneira.pruebabci.converter;

import dev.fneira.pruebabci.constant.Importance;
import dev.fneira.pruebabci.constant.Urgency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convertidor para los enums de los filtros de ofertas
 */
@Component
public class FiltersRequestConverter {

  /**
   * Convertidor para la importancia
   */
  @Component
  public static class ImportanceConverter implements Converter<String, Importance> {
    @Override
    public Importance convert(final String source) {
      return Importance.getByImportance(source);
    }
  }

  /**
   * Convertidor para la urgencia
   */
  @Component
  public static class UrgencyConverter implements Converter<String, Urgency> {
    @Override
    public Urgency convert(final String source) {
      return Urgency.getByImportance(source);
    }
  }
}
