package base.service.abstractions;

import org.springframework.jdbc.core.JdbcTemplate;


/**
 * Базовая абстракция для любого сервиса Jdbc
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @author Ivan Zhendorenko
 */
public interface BaseJdbcService<D, E> extends BaseService<D, E> {

  /**
   * jdbc
   *
   * @return JdbcTemplate
   */
  JdbcTemplate jdbc();
}
