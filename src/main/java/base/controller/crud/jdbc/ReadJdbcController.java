package base.controller.crud.jdbc;

import base.controller.abstractions.BaseController;
import base.service.jdbc.CRJdbcService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


public interface ReadJdbcController<D, E, I> extends BaseController<D, E> {

  /**
   * Сервис, используемый контроллером
   *
   * @return Сервис
   */
  @Override
  CRJdbcService<D, E, I> svc();

  @Operation(summary = "Поиск",
      description = "Поиск сущности по идентификатору",
      parameters = @Parameter(name = "id", description = "Идентификатор сущности", required = true)
  )
  @GetMapping("/find-by-id/{id}")
  default ResponseEntity<D> findById(@PathVariable I id) {
    return svc().findByIdDto(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @Operation(summary = "Поиск",
      description = "Поиск сущностей по идентификаторам",
      parameters = @Parameter(name = "ids", description = "Идентификаторы искомых сущностей в виде id1,id2,id3", required = true)
  )
  @GetMapping("/find-all-by-id")
  default ResponseEntity<List<D>> findAllById(@RequestParam List<I> ids) {
    List<D> results = svc().findAllByIdDto(ids);
    if (results.isEmpty()) {
      return ResponseEntity.noContent().build();  // Если список пуст, возвращаем 204 No Content
    }
    return ResponseEntity.ok(results);  // Если найдены, возвращаем 200 OK
  }

  @Operation(summary = "Поиск всех сущностей")
  @GetMapping("/find-all")
  default ResponseEntity<List<D>> findAll() {
    List<D> results = svc().findAllDto();
    if (results.isEmpty()) {
      return ResponseEntity.noContent().build();  // Если список пуст, возвращаем 204 No Content
    }
    return ResponseEntity.ok(results);  // Если найдены, возвращаем 200 OK
  }
}
