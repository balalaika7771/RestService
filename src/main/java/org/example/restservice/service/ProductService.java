package org.example.restservice.service;

import base.service.jdbc.CRJdbcService;
import base.service.jpa.CrudJpaService;
import base.transformer.Transformer;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.example.restservice.dto.ProductDto;
import org.example.restservice.entity.Product;
import org.example.restservice.jpa.ProductRepository;
import org.example.restservice.transformer.ProductTransformer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ProductService implements CrudJpaService<ProductDto, Product, Long>, CRJdbcService<ProductDto, Product, Long> {


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
  public ProductDto enrich(ProductDto dto) {
    return CrudJpaService.super.enrich(dto);
  }

  @Override
  public List<ProductDto> findAllDto() {
    return CRJdbcService.super.findAllDto();
  }

  @Override
  public List<ProductDto> findAllByIdDto(List<Long> ids) {
    return CRJdbcService.super.findAllByIdDto(ids);
  }

  @Override
  public List<Product> findAll() {
    return CRJdbcService.super.findAll();
  }

  @Override
  public List<Product> findAllById(List<Long> ids) {
    return CRJdbcService.super.findAllById(ids);
  }

  @Override
  public Class<Product> getEntityClass() {
    return Product.class;
  }

  @Override
  public JpaRepository<Product, Long> repo() {
    return repository;
  }

  @Override
  public void insertAll(List<Product> entities) {
    CRJdbcService.super.insertAll(entities);
  }

  @Override
  public Product save(Product entity) {
    return CrudJpaService.super.save(entity);
  }

  @Override
  public Product saveAndFlush(Product entity) {
    return CrudJpaService.super.saveAndFlush(entity);
  }

  @Override
  public List<Product> saveAll(Iterable<Product> entities) {
    return CrudJpaService.super.saveAll(entities);
  }

  @Override
  public List<Product> saveAllAndFlush(Iterable<Product> entities) {
    return CrudJpaService.super.saveAllAndFlush(entities);
  }

  @Override
  public ProductDto saveDto(Product entity) {
    return CrudJpaService.super.saveDto(entity);
  }

  @Override
  public ProductDto saveAndFlushDto(Product entity) {
    return CrudJpaService.super.saveAndFlushDto(entity);
  }

  @Override
  public List<ProductDto> saveAllDto(Iterable<Product> entities) {
    return CrudJpaService.super.saveAllDto(entities);
  }

  @Override
  public List<ProductDto> saveAllAndFlushDto(Iterable<Product> entities) {
    return CrudJpaService.super.saveAllAndFlushDto(entities);
  }

  @Override
  public void deleteById(Long id) {
    CrudJpaService.super.deleteById(id);
  }

  @Override
  public void delete(Product entity) {
    CrudJpaService.super.delete(entity);
  }

  @Override
  public void deleteAllById(Iterable<Long> ids) {
    CrudJpaService.super.deleteAllById(ids);
  }

  @Override
  public void deleteAll(Iterable<Product> entities) {
    CrudJpaService.super.deleteAll(entities);
  }

  @Override
  public void deleteAll() {
    CrudJpaService.super.deleteAll();
  }

  @Override
  public Optional<Product> findById(Long id) {
    return CrudJpaService.super.findById(id);
  }

  @Override
  public List<Product> findAllById(Iterable<Long> ids) {
    return CrudJpaService.super.findAllById(ids);
  }

  @Override
  public List<Product> findAll(Sort sort) {
    return CrudJpaService.super.findAll(sort);
  }

  @Override
  public Page<Product> findAll(Pageable pageable) {
    return CrudJpaService.super.findAll(pageable);
  }

  @Override
  public <S extends Product> Optional<S> findOne(Example<S> example) {
    return CrudJpaService.super.findOne(example);
  }

  @Override
  public <S extends Product> Iterable<S> findAll(Example<S> example) {
    return CrudJpaService.super.findAll(example);
  }

  @Override
  public <S extends Product> Iterable<S> findAll(Example<S> example, Sort sort) {
    return CrudJpaService.super.findAll(example, sort);
  }

  @Override
  public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
    return CrudJpaService.super.findAll(example, pageable);
  }

  @Override
  public <S extends Product> long count(Example<S> example) {
    return CrudJpaService.super.count(example);
  }

  @Override
  public <S extends Product> boolean exists(Example<S> example) {
    return CrudJpaService.super.exists(example);
  }

  @Override
  public <S extends Product, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return CrudJpaService.super.findBy(example, queryFunction);
  }

  @Override
  public Optional<ProductDto> findByIdDto(Long id) {
    return CrudJpaService.super.findByIdDto(id);
  }

  @Override
  public List<ProductDto> findAllByIdDto(Iterable<Long> ids) {
    return CrudJpaService.super.findAllByIdDto(ids);
  }

  @Override
  public List<ProductDto> findAllDto(Sort sort) {
    return CrudJpaService.super.findAllDto(sort);
  }

  @Override
  public Page<ProductDto> findAllDto(Pageable pageable) {
    return CrudJpaService.super.findAllDto(pageable);
  }

  @Override
  public Product update(Product entity, Consumer<Product> mutator) {
    return CrudJpaService.super.update(entity, mutator);
  }

  @Override
  public Product updateAndFlush(Product entity, Consumer<Product> mutator) {
    return CrudJpaService.super.updateAndFlush(entity, mutator);
  }

  @Override
  public List<Product> updateAll(Iterable<Product> entities, Consumer<Product> mutator) {
    return CrudJpaService.super.updateAll(entities, mutator);
  }

  @Override
  public List<Product> updateAllAndFlush(Iterable<Product> entities, Consumer<Product> mutator) {
    return CrudJpaService.super.updateAllAndFlush(entities, mutator);
  }

  @Override
  public ProductDto updateDto(Product entity, Consumer<Product> mutator) {
    return CrudJpaService.super.updateDto(entity, mutator);
  }

  @Override
  public ProductDto updateAndFlushDto(Product entity, Consumer<Product> mutator) {
    return CrudJpaService.super.updateAndFlushDto(entity, mutator);
  }

  @Override
  public List<ProductDto> updateAllDto(Iterable<Product> entities, Consumer<Product> mutator) {
    return CrudJpaService.super.updateAllDto(entities, mutator);
  }

  @Override
  public List<ProductDto> updateAllAndFlushDto(Iterable<Product> entities, Consumer<Product> mutator) {
    return CrudJpaService.super.updateAllAndFlushDto(entities, mutator);
  }
}
