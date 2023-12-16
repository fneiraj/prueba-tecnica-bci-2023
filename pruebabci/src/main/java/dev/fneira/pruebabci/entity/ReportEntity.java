package dev.fneira.pruebabci.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad del reporte de error
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "error_reports")
public class ReportEntity {
  @Id @GeneratedValue private int id;
  private String serviceName;
  private String description;
  private String errorDescription;
  private Date date;
  private String userEmail;
}
