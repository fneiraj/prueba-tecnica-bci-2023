package dev.fneira.pruebabci.service.report;

import dev.fneira.pruebabci.dto.ReportDto;
import dev.fneira.pruebabci.dto.RequestOfertaRequestDto;
import dev.fneira.pruebabci.entity.ReportEntity;
import dev.fneira.pruebabci.mapper.ReportMapper;
import dev.fneira.pruebabci.repository.ReportsRepository;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/** Implementación de servicio de reportes */
@Service
@AllArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

  /** Repositorio de reportes */
  private final ReportsRepository reportsRepository;

  /**
   * Obtiene los reportes
   *
   * @return
   */
  @Override
  public List<ReportDto> getReports() {
    return reportsRepository.findAll().stream().map(ReportMapper::toDto).toList();
  }

  /**
   * Agrega un reporte de éxito
   *
   * @param e
   * @param serviceName
   * @param username
   * @param requestOfertaRequestDto
   */
  @Override
  @Async
  public void addFailure(
      Exception e,
      String serviceName,
      String username,
      RequestOfertaRequestDto requestOfertaRequestDto) {
    log.info("adding failure to database");
    reportsRepository.save(
        ReportEntity.builder()
            .serviceName(serviceName)
            .description(
                String.format(
                    "Failure take offer id %s with paymean %s",
                    requestOfertaRequestDto.getIdOferta(),
                    requestOfertaRequestDto.getIdMedioPago()))
            .errorDescription(e.getMessage())
            .date(new Date())
            .userEmail(username)
            .build());
  }
}
