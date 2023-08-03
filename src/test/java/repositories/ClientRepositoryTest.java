package repositories;

import org.autoAtendimento.repositories.ClientRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class ClientRepositoryTest {
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fastfood_test",
                "postgres", "thais123");

        // Criar tabela client e inserir um cliente de teste
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS client (id SERIAL PRIMARY KEY, client_name VARCHAR(255))");
            statement.execute("INSERT INTO client (client_name) VALUES ('Test Client')");

            // Criar tabela orders e inserir alguns pedidos de teste (opcional)
            statement.execute("CREATE TABLE IF NOT EXISTS orders (id SERIAL PRIMARY KEY, client_id INTEGER, item_name VARCHAR(255), quantity INTEGER, price DECIMAL(10, 2))");
            statement.execute("INSERT INTO orders (client_id, item_name, quantity, price) VALUES (1, 'X-burger', 2, 20.00)");
        }
    }

    @After
    public void tearDown() throws SQLException {
        // Excluir dados de teste após cada teste
        try (Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM client");
        }
        connection.close();
    }

    @Test
    public void testGetLastInsertedClientId() {
        int lastInsertedClientId = ClientRepository.getLastInsertedClientId();
        assertEquals(1, lastInsertedClientId);
    }
}

// O método setUp() é executado antes de cada teste, criando uma conexão com o banco de dados de teste, criando uma tabela client e inserindo um cliente de teste nela. O método tearDown() é executado após cada teste, excluindo os dados de teste e fechando a conexão.
// O método testGetLastInsertedClientId() testa o método getLastInsertedClientId() e verifica se ele retorna o ID esperado do cliente de teste que inserimos anteriormente