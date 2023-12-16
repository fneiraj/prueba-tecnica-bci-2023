package dev.fneira.pruebabci.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Datos de la petición de oferta
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestOfertaRequestDto {

  private int idOferta;
  private int idMedioPago;
}
