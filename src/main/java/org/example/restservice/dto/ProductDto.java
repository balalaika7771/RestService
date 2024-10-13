package org.example.restservice.dto;

import base.abstractions.IdentifiableDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;


@Setter
@Getter
@FieldNameConstants
@NoArgsConstructor
public class ProductDto extends IdentifiableDto<ProductDto> {

  @NotNull
  private String name;

  public ProductDto(String name) {
    this.name = name;
  }
}
