package base.service.jdbc;


/**
 * Сервис со всеми CR-операциями
 *
 * @param <D> Тип дто
 * @param <E> Тип Сущности
 * @param <I> Тип идентификатора сущности
 * @author Ivan Zhendorenko
 */
public interface CRJdbcService<D, E, I> extends CreateJdbcService<D, E>, ReadJdbcService<D, E, I> {

}