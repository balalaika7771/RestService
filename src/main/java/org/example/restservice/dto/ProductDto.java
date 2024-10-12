package org.example.restservice.dto;

import base.abstractions.IdentifiableDto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;


@Setter
@Getter
@FieldNameConstants
public class ProductDto extends IdentifiableDto<ProductDto> {

  @NotNull
  private String name;
}
