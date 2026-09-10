package org.example.service;

import org.example.dto.ProductDTO;
import org.example.exception.InvalidProductException;
import org.example.exception.ProductNotFoundException;
import org.example.model.Category;
import org.example.repository.MemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(new MemoryProductRepository());
    }

    @Test
    void shouldSuccessfullyAddProduct() {
        service.addProduct(new ProductDTO("Arroz", 10, 25.0, Category.ALIMENTO));
        ProductDTO found = service.searchProduct("Arroz");
        assertEquals("Arroz", found.getName());
    }

    @Test
    void shouldThrowExceptionWhenAddProductWithEmptyName() {
        ProductDTO dto = new ProductDTO("", 10, 25.0, Category.ALIMENTO);
        assertThrows(InvalidProductException.class, () -> service.addProduct(dto));
    }

    @Test
    void shouldThrowExceptionWhenAddingProductWithNegativeQuantity() {
        ProductDTO dto = new ProductDTO("Feijao", -5, 8.0, Category.ALIMENTO);
        assertThrows(InvalidProductException.class, () -> service.addProduct(dto));
    }

    @Test
    void mustThrowExcecaoAoSearchProductNonexistent() {
        assertThrows(ProductNotFoundException.class, () -> service.searchProduct("Inexistente"));
    }

    @Test
    void shouldSuccessfullyRemoveProduct() {
        service.addProduct(new ProductDTO("Sabao", 3, 5.0, Category.LIMPEZA));
        service.removeProduct("Sabao");
        assertThrows(ProductNotFoundException.class, () -> service.searchProduct("Sabao"));
    }

    @Test
    void mustThrowExceptionWhenRemovingNonexistentProduct() {
        assertThrows(ProductNotFoundException.class, () -> service.removeProduct("Inexistente"));
    }

    @Test
    void shouldUpdateProductQuantitySuccessfully() {
        service.addProduct(new ProductDTO("Rice", 10, 25.0, Category.ALIMENTO));
        service.updateProduct("Rice", 20, null);
        ProductDTO updated = service.searchProduct("Rice");
        assertEquals(20, updated.getQuantity());
    }

    @Test
    void shouldUpdateProductPriceSuccessfully() {
        service.addProduct(new ProductDTO("Rice", 10, 25.0, Category.ALIMENTO));
        service.updateProduct("Rice", null, 30.0);
        ProductDTO updated = service.searchProduct("Rice");
        assertEquals(30.0, updated.getPrice());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithNegativeQuantity() {
        service.addProduct(new ProductDTO("Rice", 10, 25.0, Category.ALIMENTO));
        assertThrows(InvalidProductException.class,
                () -> service.updateProduct("Rice", -5, null));
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsExist() {
        List<ProductDTO> products = service.listProduct();
        assertTrue(products.isEmpty());
    }

    @Test
    void shouldListAllAddedProducts() {
        service.addProduct(new ProductDTO("Rice", 10, 25.0, Category.ALIMENTO));
        service.addProduct(new ProductDTO("Soap", 3, 5.0, Category.LIMPEZA));

        List<ProductDTO> products = service.listProduct();

        assertEquals(2, products.size());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        assertThrows(ProductNotFoundException.class,
                () -> service.updateProduct("Nonexistent", 5, null));
    }
}