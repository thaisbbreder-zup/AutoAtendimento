package org.autoAtendimento.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.autoAtendimento.connection.Connection.connection;
import static org.autoAtendimento.connection.Connection.statement;

public class ClientRepository {
    public static void newClient(String name) {
        String sql = "INSERT INTO client (client_name) VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            System.out.println("Cliente cadastrado com sucesso!\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //retorna um inteiro representando o ID do último cliente inserido ou -1 se nenhum ID for encontrado
    //A função MAX() é utilizada aqui para obter o valor mais alto de ID, que corresponderá à última linha inserida
    public static int getLastInsertedClientId() {
        String sql = "SELECT MAX(id) FROM client";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
