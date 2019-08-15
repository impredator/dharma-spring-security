package com.dharma.spring.security.persistence.dao;

import com.dharma.spring.security.persistence.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
