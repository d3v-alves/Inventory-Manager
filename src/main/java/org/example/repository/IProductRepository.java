package org.example.repository;

import org.example.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {
    void save(Product product);

    boolean remove(String name);

    Optional<Product> searchByName(String name);

    List<Product> listAll();

    void update(Product product);
}