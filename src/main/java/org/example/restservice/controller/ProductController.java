package org.example.restservice.controller;

import base.controller.crud.CrudController;
import base.service.jpa.CrudJpaService;
import lombok.RequiredArgsConstructor;
import org.example.restservice.dto.ProductDto;
import org.example.restservice.entity.Product;
import org.example.restservice.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController implements CrudController<ProductDto, Product, Long> {

  private final ProductService productService;

  @Override
  public CrudJpaService<ProductDto, Product, Long> svc() {
    return productService;
  }
}
