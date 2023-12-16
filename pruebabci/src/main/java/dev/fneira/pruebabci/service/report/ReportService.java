package dev.fneira.pruebabci.service.report;

import dev.fneira.pruebabci.dto.ReportDto;
import dev.fneira.pruebabci.dto.RequestOfertaRequestDto;

import java.util.List;

/**
 * Servicio de reportes
 */
public interface ReportService {

  /**
   * Obtiene los reportes
   * @return
   */
  List<ReportDto> getReports();

  /**
   * Agrega un reporte de éxito
   * @param e
   * @param serviceName
   * @param username
   * @param requestOfertaRequestDto
   */
  void addFailure(
      Exception e,
      String serviceName,
      String username,
      RequestOfertaRequestDto requestOfertaRequestDto);
}
