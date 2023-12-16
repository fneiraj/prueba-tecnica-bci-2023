package dev.fneira.pruebabci.mapper;

import dev.fneira.pruebabci.dto.ReportDto;
import dev.fneira.pruebabci.entity.ReportEntity;

/**
 * Clase que mapea los atributos de un ReportEntity a un ReportDto
 */
public class ReportMapper {

  private ReportMapper() {}

  public static ReportDto toDto(ReportEntity report) {
    return ReportDto.builder()
        .id(report.getId())
        .serviceName(report.getServiceName())
        .description(report.getDescription())
        .errorDescription(report.getErrorDescription())
        .date(report.getDate())
        .userEmail(report.getUserEmail())
        .build();
  }
}
