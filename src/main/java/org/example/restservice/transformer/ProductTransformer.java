package org.example.restservice.transformer;

import base.transformer.Transformer;
import org.example.restservice.dto.ProductDto;
import org.example.restservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface ProductTransformer extends Transformer<ProductDto, Product> {

  @Mapping(source = "id", target = "id")
  ProductDto entityToDto(Product product);

  @Mapping(source = "id", target = "id")
  Product dtoToEntity(ProductDto productDto);
}
