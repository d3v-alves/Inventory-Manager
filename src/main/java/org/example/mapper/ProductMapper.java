package org.example.mapper;

import org.example.dto.ProductDTO;
import org.example.model.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(product.getName(), product.getQuantity(), product.getPrice(), product.getCategory());
    }

    public static Product toEntity(ProductDTO dto) {
        return new Product(dto.getName(), dto.getQuantity(), dto.getPrice(), dto.getCategory());
    }
}
