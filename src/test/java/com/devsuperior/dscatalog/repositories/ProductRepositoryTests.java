package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countProducts;

    @BeforeEach
    public void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countProducts = 25L;
    }

    @Test
    public void findByIdShouldReturnObjectWhenExistingId() {

        Optional<Product> product = repository.findById(existingId);

        Assertions.assertNotNull(product.get().getId());
        Assertions.assertEquals(1L, product.get().getId());
    }

    @Test
    public void findByIdShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExists() {

        Optional<Product> product = repository.findById(nonExistingId);
        Assertions.assertTrue(product.isEmpty());
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countProducts + 1, product.getId());
    }


    @Test
    public void deleteShouldDeleteObjectWhenExistingId() {

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyResultDataExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });


    }
}
