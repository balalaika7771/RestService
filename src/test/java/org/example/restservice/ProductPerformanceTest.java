package org.example.restservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.example.restservice.dto.ProductDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductPerformanceTest {

  @Autowired
  private WebTestClient webTestClient;

  private ExecutorService executorService;

  @BeforeAll
  public void setup() {
    int THREAD_POOL_SIZE = 8;
    executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
  }

  @AfterAll
  public void teardown() {
    executorService.shutdown();
  }

  // Method to calculate percentiles
  private double getPercentile(List<Long> sortedTimes, double percentile) {
    if (sortedTimes.isEmpty()) {
      return 0;
    }
    int index = (int) Math.ceil(percentile / 100.0 * sortedTimes.size()) - 1;
    return sortedTimes.get(index);
  }

  // Method to calculate and print statistics
  private void calculateAndPrintStatistics(List<Long> responseTimes, Long startTime) {
    Collections.sort(responseTimes);

    double median = getPercentile(responseTimes, 50) - startTime;
    double p95 = getPercentile(responseTimes, 95) - startTime;
    double p99 = getPercentile(responseTimes, 99) - startTime;
    double maxTime = responseTimes.getLast() - startTime;

    System.out.println("Median Time (50%): " + median + " ms");
    System.out.println("95th Percentile: " + p95 + " ms");
    System.out.println("99th Percentile: " + p99 + " ms");
    System.out.println("Max Time (100%): " + maxTime + " ms");
  }

  // Test for JDBC implementation with corrections
  @Test
  public void testPerformanceJdbc() throws InterruptedException {
    // Step 1: Save 100,000 records in a single request via save-all
    int totalRecords = 100_000;
    System.out.println("Starting JDBC save-all test...");

    // Create a list of products
    List<ProductDto> products = IntStream.range(0, totalRecords)
                                         .mapToObj(i -> new ProductDto("Product JDBC " + i))
                                         .toList();

    long saveStartTime = System.currentTimeMillis();

    webTestClient.post()
                 .uri("/api/product/jdbc/save-all")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(products)
                 .exchange()
                 .expectStatus().isOk();

    long saveEndTime = System.currentTimeMillis();
    long totalSaveTime = saveEndTime - saveStartTime;
    System.out.println("Total Save Time (JDBC): " + totalSaveTime + " ms");

    // Step 2: Read 1,000,000 random records with 100 concurrent connections
    System.out.println("Starting JDBC read test...");
    int batchSize = 10_000; // Batch size
    int batches = 100;

    List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());

    Long startTime = System.currentTimeMillis();

    CountDownLatch readLatch = new CountDownLatch(batches);
    for (int batch = 0; batch < batches; batch++) {

      int finalBatch = batch;
      executorService.submit(() -> {

        List<Long> randomIds = new Random().longs(batchSize, 1, (long) totalRecords + 1).boxed().toList();

        randomIds.forEach(id -> {
          webTestClient.get()
                       .uri("/api/product/jdbc/find-by-id/{id}", id)
                       .exchange()
                       .expectStatus().isOk()
                       .expectBody(byte[].class)
                       .returnResult();

          responseTimes.add(System.currentTimeMillis());
        });

        readLatch.countDown();
        System.out.println("Batch " + (finalBatch + 1) + " of " + batches + " completed");
      });
    }
    readLatch.await();
    // Calculate and print statistics
    calculateAndPrintStatistics(responseTimes, startTime);
  }

  // Similar test for JPA implementation
  @Test
  public void testPerformanceJpa() throws InterruptedException {
    // Step 1: Save 100,000 records in a single request via save-all
    int totalRecords = 100_000;
    System.out.println("Starting JPA save-all test...");

    // Create a list of products
    List<ProductDto> products = IntStream.range(0, totalRecords)
                                         .mapToObj(i -> new ProductDto("Product JPA " + i))
                                         .toList();

    long saveStartTime = System.currentTimeMillis();

    webTestClient.post()
                 .uri("/api/product/save-all")
                 .contentType(MediaType.APPLICATION_JSON)
                 .bodyValue(products)
                 .exchange()
                 .expectStatus().isOk();

    long saveEndTime = System.currentTimeMillis();
    long totalSaveTime = saveEndTime - saveStartTime;
    System.out.println("Total Save Time (JPA): " + totalSaveTime + " ms");

    // Step 2: Read 1,000,000 random records with 100 concurrent connections
    System.out.println("Starting JPA read test...");
    int batchSize = 10_000; // Batch size
    int batches = 100;

    List<Long> responseTimes = Collections.synchronizedList(new ArrayList<>());

    Long startTime = System.currentTimeMillis();

    CountDownLatch readLatch = new CountDownLatch(batches);
    for (int batch = 0; batch < batches; batch++) {

      int finalBatch = batch;
      executorService.submit(() -> {

        List<Long> randomIds = new Random().longs(batchSize, 1, (long) totalRecords + 1).boxed().toList();

        randomIds.forEach(id -> {
          webTestClient.get()
                       .uri("/api/product/find-by-id/{id}", id)
                       .exchange()
                       .expectStatus().isOk()
                       .expectBody(byte[].class)
                       .returnResult();

          responseTimes.add(System.currentTimeMillis());
        });

        readLatch.countDown();
        System.out.println("Batch " + (finalBatch + 1) + " of " + batches + " completed");
      });

    }
    readLatch.await();
    // Calculate and print statistics
    calculateAndPrintStatistics(responseTimes, startTime);
  }
}
