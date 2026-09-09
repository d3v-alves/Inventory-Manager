package org.example.service;

import org.example.dto.ProductDTO;
import org.example.exception.InvalidProductException;
import org.example.exception.ProductNotFoundException;
import org.example.mapper.ProductMapper;
import org.example.model.Product;
import org.example.repository.IProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final IProductRepository repository;

    public ProductService(IProductRepository repository) {
        this.repository = repository;
    }

    public void addProduct(ProductDTO dto) {
        validate(dto);
        Product product = ProductMapper.toEntity(dto);
        repository.save(product);
    }

    public void removeProduct(String name) {
        boolean removed = repository.remove(name);
        if (!removed) {
            throw new ProductNotFoundException(name);
        }
    }

    public ProductDTO searchProduct(String name) {
        Product product = repository.searchByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name));
        return ProductMapper.toDTO(product);
    }

    public void updateProduct(String name, Integer newQuantity, Double newPrice) {
        Product product = repository.searchByName(name)
                .orElseThrow(() -> new ProductNotFoundException(name));

        if (newQuantity != null) {
            if (newQuantity < 0) {
                throw new InvalidProductException("Quantity cannot be negative.");
            }
            product.setQuantity(newQuantity);
        }

        if (newPrice != null) {
            if (newPrice < 0) {
                throw new InvalidProductException("Price cannot be negative.");
            }
            product.setPrice(newPrice);
        }

        repository.update(product);
    }

    public List<ProductDTO> listProduct() {
        return repository.listAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void validate(ProductDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new InvalidProductException("Nome do produto nao pode ser vazio.");
        }
        if (dto.getQuantity() < 0) {
            throw new InvalidProductException("Quantidade nao pode ser negativa.");
        }
        if (dto.getPrice() < 0) {
            throw new InvalidProductException("Preco nao pode ser negativo.");
        }
        if (dto.getCategory() == null) {
            throw new InvalidProductException("Categoria e obrigatoria.");
        }
    }
}