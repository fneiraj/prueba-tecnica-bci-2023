package dev.fneira.pruebabci.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Datos del reporte de error
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReportDto {
  private int id;
  private String serviceName;
  private String description;
  private String errorDescription;
  private Date date;
  private String userEmail;
}
