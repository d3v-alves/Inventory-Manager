package org.example.repository;

import org.example.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class MemoryProductRepository implements IProductRepository {
    private final List<Product> products = new ArrayList<>();


    @Override
    public void update(Product product){}

    @Override
    public void save(Product product) {
        products.add(product);
    }

    @Override
    public boolean remove(String name) {
        return products.removeIf(p -> p.getName().equalsIgnoreCase(name));
    }

    @Override
    public Optional<Product> searchByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Product> listAll() {
        return products;
    }
}