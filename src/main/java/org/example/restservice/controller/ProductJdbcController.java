package org.example.restservice.controller;

import base.controller.crud.jdbc.CRJdbcController;
import base.service.jdbc.CRJdbcService;
import lombok.RequiredArgsConstructor;
import org.example.restservice.dto.ProductDto;
import org.example.restservice.entity.Product;
import org.example.restservice.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product/jdbc")
@RequiredArgsConstructor
public class ProductJdbcController implements CRJdbcController<ProductDto, Product, Long> {

  private final ProductService productService;

  @Override
  public CRJdbcService<ProductDto, Product, Long> svc() {
    return productService;
  }
}
