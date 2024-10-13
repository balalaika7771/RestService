package base.controller.crud.jdbc;


import base.service.jdbc.CRJdbcService;


/**
 * Контроллер с CR-операциями
 *
 * @author Ivan Zhendorenko
 */
public interface CRJdbcController<D, E, I> extends ReadJdbcController<D, E, I>, BatchCreateController<D, E, I> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  @Override
  CRJdbcService<D, E, I> svc();
}
