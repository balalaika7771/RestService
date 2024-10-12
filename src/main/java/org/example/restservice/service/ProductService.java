package org.example.restservice.service;

import base.service.jdbc.CreateJdbcService;
import base.service.jpa.CrudJpaService;
import base.transformer.Transformer;
import lombok.RequiredArgsConstructor;
import org.example.restservice.dto.ProductDto;
import org.example.restservice.entity.Product;
import org.example.restservice.jpa.ProductRepository;
import org.example.restservice.transformer.ProductTransformer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService implements CrudJpaService<ProductDto, Product, Long>, CreateJdbcService<ProductDto, Product> {


  private final JdbcTemplate jdbcTemplate;

  private final ProductTransformer transformer;

  private final ProductRepository repository;

  @Override
  public JdbcTemplate jdbc() {
    return jdbcTemplate;
  }

  @Override
  public Transformer<ProductDto, Product> t() {
    return transformer;
  }

  @Override
  public JpaRepository<Product, Long> repo() {
    return repository;
  }
}
