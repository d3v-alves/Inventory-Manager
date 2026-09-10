package org.example.dto;

import org.example.model.Category;

public class ProductDTO {

    private final String name;
    private final int quantity;
    private final double price;
    private final Category category;
    
    public ProductDTO(String name, int quantity, double price, Category category) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product: " + name + " | Qtd: " + quantity + " | Price; $" + price + " |  Category: " + category;
    }
}
