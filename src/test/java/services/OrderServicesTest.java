package services;

import org.autoAtendimento.connection.Connection;
import org.autoAtendimento.repositories.ClientRepository;
import org.autoAtendimento.services.OrderServices;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class OrderServicesTest {
    @Before
    public void setUp() throws SQLException {
        Connection.getConnection(); // Estabelece a conexão antes de iniciar o teste
    }
    @Test
    public void testOrderSandwich() {
        // Cenário de teste
        int optionSandwich = 1; // X-burger
        int sandwichQuantity = 2;
        String clientName = "Eliane";
        ClientRepository.newClient(clientName);
        int clientId = ClientRepository.getLastInsertedClientId();

        // Executa o método que queremos testar
        OrderServices.orderSandwich(optionSandwich, sandwichQuantity, clientId);

        // Verifica se o pedido foi inserido corretamente no banco de dados de teste
        // Verifica se o preço final do pedido é calculado corretamente.
        double expectedFinalPrice = 2 * 10.00; // Quantidade * Preço do X-burger
        double actualFinalPrice = OrderServices.getTotalOrderPrice(clientId);
        assertEquals(expectedFinalPrice, actualFinalPrice, 0.01);
    }

    @Test
    public void testOrderDrink() {
        // Cenário de teste
        int optionDrink = 1; // Refrigerante
        int drinkQuantity = 3;
        String clientName = "Maria";
        ClientRepository.newClient(clientName);
        int clientId = ClientRepository.getLastInsertedClientId();

        // Executa o método que queremos testar
        OrderServices.orderDrink(optionDrink, drinkQuantity, clientId);

        double expectedFinalPrice = 3 * 8.00; // Quantidade * Preço do Refrigerante
        double actualFinalPrice = OrderServices.getTotalOrderPrice(clientId);
        assertEquals(expectedFinalPrice, actualFinalPrice, 0.01); // Utilizamos uma tolerância de 0.01 devido a possíveis arredondamentos de ponto flutuante.
    }
}