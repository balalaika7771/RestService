package org.example.restservice.transformer;

import base.transformer.Transformer;
import org.example.restservice.dto.ProductDto;
import org.example.restservice.entity.Product;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface ProductTransformer extends Transformer<ProductDto, Product> {

  ProductDto entityToDto(Product product);

  Product dtoToEntity(ProductDto productDto);
}
