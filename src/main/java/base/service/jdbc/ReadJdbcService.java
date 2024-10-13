package base.service.jdbc;

import base.service.abstractions.BaseJdbcService;
import base.transformer.Transformer;
import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


/**
 * Сервис, способный читать сущности из базы данных и трансформировать их в DTO
 *
 * @param <D> Тип дто
 * @param <E> Тип сущности
 * @author Ivan Zhendorenko
 */
public interface ReadJdbcService<D, E, I> extends BaseJdbcService<D, E> {

  Transformer<D, E> t();

  // Кеш для SQL-запросов
  Map<String, String> sqlCache = new HashMap<>();

  /**
   * Возвращает все DTO.
   *
   * @return список всех DTO
   */
  default List<D> findAllDto() {
    return t().entitiesToDtos(findAll());
  }

  /**
   * Возвращает DTO по идентификатору.
   *
   * @param id идентификатор сущности
   * @return DTO, если найдено
   */
  default Optional<D> findByIdDto(I id) {
    return Optional.of(t().entityToDto(findById(id).orElse(null)));
  }

  /**
   * Возвращает список DTO по идентификаторам.
   *
   * @param ids список идентификаторов
   * @return список DTO
   */
  default List<D> findAllByIdDto(List<I> ids) {
    return t().entitiesToDtos(findAllById(ids));
  }

  /**
   * Возвращает все сущности.
   *
   * @return список всех сущностей
   */
  default List<E> findAll() {
    String tableName = getTableName();
    String sqlKey = "findAll";  // Ключ для кеша SQL-запроса
    String sql = sqlCache.computeIfAbsent(sqlKey, key -> String.format("SELECT * FROM %s", tableName));

    RowMapper<E> rowMapper = new BeanPropertyRowMapper<>(getEntityClass());
    return jdbc().query(sql, rowMapper);
  }

  /**
   * Возвращает сущность по идентификатору.
   *
   * @param id идентификатор сущности
   * @return сущность, если найдена
   */
  default Optional<E> findById(I id) {
    String tableName = getTableName();
    Field idField = getIdField(getEntityClass());
    String idColumnName = idField.isAnnotationPresent(Column.class) ? idField.getAnnotation(Column.class).name() : idField.getName();

    String sqlKey = "findById_" + idColumnName;  // Ключ для кеша SQL-запроса
    String sql = sqlCache.computeIfAbsent(sqlKey, key -> String.format("SELECT * FROM %s WHERE %s = ?", tableName, idColumnName));

    RowMapper<E> rowMapper = new BeanPropertyRowMapper<>(getEntityClass());

    List<E> result = jdbc().query(sql, rowMapper, id);
    return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
  }

  /**
   * Возвращает список сущностей по идентификаторам.
   *
   * @param ids список идентификаторов
   * @return список сущностей
   */
  default List<E> findAllById(List<I> ids) {
    String tableName = getTableName();
    Field idField = getIdField(getEntityClass());
    String idColumnName = idField.isAnnotationPresent(Column.class) ? idField.getAnnotation(Column.class).name() : idField.getName();

    String placeholders = ids.stream().map(id -> "?").collect(Collectors.joining(", "));
    String sqlKey = "findAllById_" + idColumnName;  // Ключ для кеша SQL-запроса
    String sql = sqlCache.computeIfAbsent(sqlKey, key -> String.format("SELECT * FROM %s WHERE %s IN (%s)", tableName, idColumnName, placeholders));

    RowMapper<E> rowMapper = new BeanPropertyRowMapper<>(getEntityClass());
    return jdbc().query(sql, rowMapper, ids.toArray());
  }

  /**
   * Возвращает класс сущности.
   *
   * @return класс сущности
   */
  Class<E> getEntityClass();

  /**
   * Определение имени таблицы на основе класса сущности.
   */
  private String getTableName() {
    return getEntityClass().getSimpleName().toLowerCase();
  }

  /**
   * Получение поля с аннотацией @Id для поиска по идентификатору.
   */
  private static Field getIdField(Class<?> entityClass) {
    return List.of(entityClass.getDeclaredFields()).stream()
               .filter(field -> field.isAnnotationPresent(Id.class))
               .findFirst()
               .orElseThrow(() -> new RuntimeException("Поле с аннотацией @Id не найдено в классе " + entityClass.getName()));
  }
}
