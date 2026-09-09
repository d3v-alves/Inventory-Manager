package org.example;

import org.example.dto.ProductDTO;
import org.example.exception.InvalidProductException;
import org.example.exception.ProductNotFoundException;
import org.example.model.Category;
import org.example.repository.ProductRepositoryFile;
import org.example.service.ProductService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductService service = new ProductService(new ProductRepositoryFile("estoque.csv"));
        int option;

        do {
            System.out.println("\n--- Gerenciador de Estoque ---");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Remover produto");
            System.out.println("3 - Listar produtos");
            System.out.println("4 - Buscar produto");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");
            option = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (option) {
                    case 1:
                        System.out.print("Nome do produto: ");
                        String name = scanner.nextLine();
                        System.out.print("Quantidade: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Preco: ");
                        double price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Categoria (ALIMENTO, ELETRONICO, VESTUARIO, LIMPEZA, OUTROS): ");
                        Category category = Category.valueOf(scanner.nextLine().toUpperCase());

                        service.addProduct(new ProductDTO(name, quantity, price, category));
                        System.out.println("Produto adicionado!");
                        break;

                    case 2:
                        System.out.print("Nome do produto a remover: ");
                        String removeName = scanner.nextLine();
                        service.removeProduct(removeName);
                        System.out.println("Produto removido!");
                        break;

                    case 3:
                        service.listProduct().forEach(System.out::println);
                        break;

                    case 4:
                        System.out.print("Nome do produto a buscar: ");
                        String searchName = scanner.nextLine();
                        System.out.println(service.searchProduct(searchName));
                        break;

                    case 5:
                        System.out.print("Product name to update: ");
                        String nameUpdate = scanner.nextLine();

                        System.out.print("New quantity (leave blank to keep current): ");
                        String quantityInput = scanner.nextLine();
                        Integer newQuantity = quantityInput.isBlank() ? null : Integer.parseInt(quantityInput);

                        System.out.print("New price (leave blank to keep current): ");
                        String priceInput = scanner.nextLine();
                        Double newPrice = priceInput.isBlank() ? null : Double.parseDouble(priceInput);

                        service.updateProduct(nameUpdate, newQuantity, newPrice);
                        System.out.println("Product updated!");
                        break;

                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (InvalidProductException | ProductNotFoundException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Categoria invalida.");
            }

        } while (option != 0);

        scanner.close();
    }
}