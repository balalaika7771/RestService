package org.example.restservice;


import org.example.restservice.dto.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class ProductApiTest {

  @Autowired
  private WebTestClient webTestClient;

  private Long savedProductId;

  @BeforeEach
  public void setUp() {
    // Создаем продукт для тестов чтения и удаления
    ProductDto productDto = new ProductDto();
    productDto.setName("Test Product");

    ProductDto createdProduct = webTestClient.post()
                                             .uri("/api/product/save")
                                             .contentType(MediaType.APPLICATION_JSON)
                                             .bodyValue(productDto)
                                             .exchange()
                                             .expectStatus().isOk()
                                             .expectBody(ProductDto.class)
                                             .returnResult()
                                             .getResponseBody();

    assert createdProduct != null;
    savedProductId = createdProduct.getId();
  }

  @Test
  public void testSaveProduct() {
    ProductDto productDto = new ProductDto();
    productDto.setName("New Product");

    webTestClient.post()
                 .uri("/api/product/save")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(productDto)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.name").isEqualTo("New Product");
  }

  @Test
  public void testFindProductById() {
    webTestClient.get()
                 .uri("/api/product/find-by-id/{id}", savedProductId)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.id").isEqualTo(savedProductId)
                 .jsonPath("$.name").isEqualTo("Test Product");
  }

  @Test
  public void testUpdateProduct() {
    ProductDto updatedProduct = new ProductDto();
    updatedProduct.setId(savedProductId);
    updatedProduct.setName("Updated Product");

    webTestClient.patch()
                 .uri("/api/product/update")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(updatedProduct)
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.name").isEqualTo("Updated Product");
  }

  @Test
  public void testFindAllProducts() {
    webTestClient.get()
                 .uri("/api/product/find-all")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody()
                 .jsonPath("$.content.length()").isNotEmpty();
  }

  @Test
  public void testDeleteProduct() {
    webTestClient.delete()
                 .uri("/api/product/delete-by-id/{id}", savedProductId)
                 .exchange()
                 .expectStatus().isOk();

    // Проверяем, что продукт действительно удален
    webTestClient.get()
                 .uri("/api/product/find-by-id/{id}", savedProductId)
                 .exchange()
                 .expectStatus().isNotFound();
  }
}

