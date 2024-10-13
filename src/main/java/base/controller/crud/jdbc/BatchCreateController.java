package base.controller.crud.jdbc;

import base.controller.abstractions.BaseController;
import base.service.jdbc.CRJdbcService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Контроллер с пакетной create-операцией
 *
 * @author Ivan Zhendorenko
 */
public interface BatchCreateController<D, E, I> extends BaseController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  @Override
  CRJdbcService<D, E, I> svc();

  @Operation(summary = "Сохранение большой коллекции",
      description = "Сохранение большой коллекции сущностей",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "Коллекция с сохраняемыми сущностью",
          required = true
      )
  )
  @PostMapping("/save-all")
  default void saveAllBatch(@RequestBody Collection<D> dtos) {
    var entities = svc().t().dtosToEntities(dtos);
    svc().insertAll(entities);
  }
}
