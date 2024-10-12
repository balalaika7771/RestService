package org.example.restservice.controller;

import base.controller.crud.BatchCreateController;
import base.service.jdbc.CreateJdbcService;
import lombok.RequiredArgsConstructor;
import org.example.restservice.dto.ProductDto;
import org.example.restservice.entity.Product;
import org.example.restservice.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductBatchController implements BatchCreateController<ProductDto, Product> {

  private final ProductService productService;

  @Override
  public CreateJdbcService<ProductDto, Product> svc() {
    return productService;
  }
}
