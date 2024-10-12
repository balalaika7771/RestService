package base.service.jdbc;

import base.service.abstractions.BaseJdbcService;
import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;


/**
 * Сервис, способный создавать сущности
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @author Ivan Zhendorenko
 */
public interface CreateJdbcService<D, E> extends BaseJdbcService<D, E> {

  /**
   * Выполняет пакетную вставку.
   *
   * @param entities список сущностей для вставки
   */
  default void insertAll(List<E> entities) {
    if (entities.isEmpty()) {
      return;
    }

    // Получаем первый объект для анализа структуры класса
    E firstEntity = entities.get(0);
    Class<?> entityClass = firstEntity.getClass();

    // Генерируем SQL-запрос для вставки
    String tableName = entityClass.getSimpleName().toLowerCase();
    List<Field> fields = getFieldsWithoutId(entityClass);
    String fieldNames = fields.stream()
                              .map(field -> field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).name() : field.getName())
                              .collect(Collectors.joining(", "));
    String placeholders = fields.stream()
                                .map(field -> "?")
                                .collect(Collectors.joining(", "));
    String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, fieldNames, placeholders);

    // Преобразуем сущности в массивы аргументов для PreparedStatement
    List<Object[]> batchArgs = entities.stream()
                                       .map(entity -> fields.stream()
                                                            .map(field -> getFieldValue(entity, field))
                                                            .toArray())
                                       .collect(Collectors.toList());

    // Выполняем пакетную вставку
    jdbc().batchUpdate(sql, batchArgs);
  }

  /**
   * Возвращает список полей, кроме поля с аннотацией @Id.
   */
  private static List<Field> getFieldsWithoutId(Class<?> entityClass) {
    return List.of(entityClass.getDeclaredFields()).stream()
               .filter(field -> !field.isAnnotationPresent(Id.class))
               .collect(Collectors.toList());
  }

  /**
   * Возвращает значение поля сущности через рефлексию.
   */
  private static Object getFieldValue(Object entity, Field field) {
    try {
      field.setAccessible(true);
      return field.get(entity);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Не удалось получить значение поля: " + field.getName(), e);
    }
  }
}
