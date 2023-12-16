package dev.fneira.pruebabci.repository;

import dev.fneira.pruebabci.entity.ReportEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repositorio de usuarios
 */
public interface ReportsRepository extends CrudRepository<ReportEntity, Integer> {

  List<ReportEntity> findAll();
}
