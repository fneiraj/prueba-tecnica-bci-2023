package dev.fneira.pruebabci.controller;

import dev.fneira.pruebabci.dto.ReportDto;
import dev.fneira.pruebabci.service.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para los reportes de errores de las solicitudes de oferta
 */
@RestController
@RequestMapping("reportes")
@RequiredArgsConstructor
public class ReportsController {

  /**
   * Servicio para los reportes
   */
  private final ReportService reportService;

  /**
   * Obtener todas las solicitudes de oferta que han fallado
   * @return Lista de solicitudes de oferta que han fallado
   */
  @Operation(summary = "Obtener todas las solicitudes de oferta que han fallado")
  @GetMapping("/solicitudes-fallidas")
  public List<ReportDto> getFailed() {
    return reportService.getReports();
  }
}
