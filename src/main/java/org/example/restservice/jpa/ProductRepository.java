package org.example.restservice.jpa;

import org.example.restservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Репозиторий для работы с нашей ентити
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
