package org.autoAtendimento.services;

import org.autoAtendimento.repositories.ClientRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static org.autoAtendimento.connection.Connection.connection;
import static org.autoAtendimento.repositories.ClientRepository.getLastInsertedClientId;

public class OrderServices {
    public static void orderSandwich(int optionSandwich, int sandwichQuantity, int clientId) {
        String sandwichName;
        double sandwichPrice;
        if (optionSandwich == 1) {
            sandwichName = "X-burger";
            sandwichPrice = 10.00;
        } else if (optionSandwich == 2) {
            sandwichName = "X-salada";
            sandwichPrice = 12.00;
        } else {
            System.out.println("Opção inválida, retornando ao menu.");
            return;
        }
        double finalPrice = sandwichQuantity * sandwichPrice;

        String sql = "INSERT INTO orders (client_id, item_name, item_price, quantity, order_price) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, getLastInsertedClientId());
            preparedStatement.setString(2, sandwichName);
            preparedStatement.setDouble(3, sandwichPrice);
            preparedStatement.setInt(4, sandwichQuantity);
            preparedStatement.setDouble(5, finalPrice);
            preparedStatement.executeUpdate();

            viewOrders(clientId);
            System.out.println("\nPedido realizado com sucesso!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void orderDrink(int optionDrink, int drinkQuantity, int clientId) {
        String drinkName;
        double drinkPrice;
        if (optionDrink == 1) {
            drinkName = "Refrigerante";
            drinkPrice = 8.00;
        } else if (optionDrink == 2) {
            drinkName = "Suco";
            drinkPrice = 6.00;
        } else {
            System.out.println("Opção inválida, retornando ao menu.");
            return;
        }
        double finalPrice = drinkQuantity * drinkPrice;

        String sql = "INSERT INTO orders (client_id, item_name, item_price, quantity, order_price) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ClientRepository.getLastInsertedClientId());
            preparedStatement.setString(2, drinkName);
            preparedStatement.setDouble(3, drinkPrice);
            preparedStatement.setInt(4, drinkQuantity);
            preparedStatement.setDouble(5, finalPrice);
            preparedStatement.executeUpdate();

            viewOrders(clientId);
            System.out.println("\nPedido realizado com sucesso!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewOrders(int clientId) {
        String sql = "SELECT o.id, c.client_name, o.item_name, o.quantity, o.item_price, o.order_price " + "FROM client c INNER JOIN orders o ON c.id = o.client_id " + "WHERE c.id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clientId); // Set the clientId as a parameter
            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("\n======================================== ORDERS ========================================");
            System.out.printf("%-10s %-20s %-20s %-10s %-10s %-10s\n", "Código", "Client Name", "Item", "Quant ", "Price", "Final Price");
            while (rs.next()) {
                int orderId = rs.getInt("id");
                String clientName = rs.getString("client_name");
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                double itemPrice = rs.getDouble("item_price");
                double finalPrice = rs.getDouble("order_price");

                System.out.printf("%-10d %-20s %-20s %-10d $%-10.2f $%-10.2f\n", orderId, clientName, itemName, quantity, itemPrice, finalPrice);
            }
            System.out.println("\nO valor total do pedido é: " + getTotalOrderPrice(clientId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editOrderItem(int clientId) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("\nDigite o código do produto que deseja editar: ");
        int productId = userInput.nextInt();

        try {
            String selectSql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, productId);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                String itemName = rs.getString("item_name");
                int currentQuantity = rs.getInt("quantity");
                double itemPrice = rs.getDouble("item_price");
                double currentFinalPrice = rs.getDouble("order_price");

                System.out.println("Produto encontrado: " + itemName);
                System.out.println("Quantidade atual: " + currentQuantity);
                System.out.println("Preço unitário: $" + itemPrice);
                System.out.println("Preço total atual: $" + currentFinalPrice);

                System.out.println("\nDigite a nova quantidade de itens: ");
                int newQuantity = userInput.nextInt();

                if (newQuantity >= 0) {
                    double newFinalPrice = itemPrice * newQuantity;

                    String updateSql = "UPDATE orders SET quantity = ?, order_price = ? WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                    updateStatement.setInt(1, newQuantity);
                    updateStatement.setDouble(2, newFinalPrice);
                    updateStatement.setInt(3, productId);
                    updateStatement.executeUpdate();

                    System.out.println("Quantidade atualizada com sucesso!");
                    viewOrders(clientId);
                } else {
                    System.out.println("Quantidade inválida. A quantidade deve ser um valor não negativo.");
                }
            } else {
                System.out.println("Produto não encontrado com o código fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeOrderItem(int clientId) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("\nDigite o código do produto que deseja remover: ");
        int productId = userInput.nextInt();

        try {
            String selectSql = "SELECT * FROM orders WHERE id = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSql);
            selectStatement.setInt(1, productId);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                String itemName = rs.getString("item_name");
                int quantity = rs.getInt("quantity");
                double itemPrice = rs.getDouble("item_price");

                System.out.println("\nProduto encontrado: " + itemName);
                System.out.println("Quantidade: " + quantity);
                System.out.println("Preço unitário: $" + itemPrice);

                System.out.println("\nDeseja realmente remover este produto do carrinho? (S/N)");
                String confirmation = userInput.next().toUpperCase();

                if (confirmation.equals("S")) {
                    String deleteSql = "DELETE FROM orders WHERE id = ?";
                    PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                    deleteStatement.setInt(1, productId);
                    deleteStatement.executeUpdate();

                    System.out.println("Produto removido do carrinho com sucesso!");
                    viewOrders(clientId);
                } else {
                    System.out.println("Operação de remoção cancelada.");
                }
            } else {
                System.out.println("Produto não encontrado com o código fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getTotalOrderPrice(int clientId) {
        String sql = "SELECT SUM(order_price) FROM orders WHERE client_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, clientId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
