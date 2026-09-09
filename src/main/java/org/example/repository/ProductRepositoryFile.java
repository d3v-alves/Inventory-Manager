package org.example.repository;

import org.example.model.Category;
import org.example.model.Product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryFile implements IProductRepository {
    private final Path archive;
    private final List<Product> products = new ArrayList<>();

    public ProductRepositoryFile(String filePath) {
        this.archive = Paths.get(filePath);
        toLoad();
    }

    @Override
    public void save(Product product) {
        products.add(product);
        persist();
    }

    @Override
    public boolean remove(String name) {
        boolean removed = products.removeIf(p -> p.getName().equalsIgnoreCase(name));
        if (removed) {
            persist();
        }
        return removed;
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

    @Override
    public void update(Product product) {
        persist();
    }

    private void toLoad() {
        if (!Files.exists(archive)) {
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(archive)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] campos = line.split(",");
                String name = campos[0];
                int quantity = Integer.parseInt(campos[1]);
                double price = Double.parseDouble(campos[2]);
                Category category = Category.valueOf(campos[3]);
                products.add(new Product(name, quantity, price, category));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar estoque do arquivo.", e);
        }
    }

    private void persist() {
        try (BufferedWriter writer = Files.newBufferedWriter(archive)) {
            for (Product p : products) {
                writer.write(String.join(",",
                        p.getName(),
                        String.valueOf(p.getQuantity()),
                        String.valueOf(p.getPrice()),
                        p.getCategory().name()));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar estoque no arquivo.", e);
        }
    }
}