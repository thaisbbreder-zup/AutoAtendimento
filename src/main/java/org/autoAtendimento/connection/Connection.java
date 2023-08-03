package org.autoAtendimento.connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    public static java.sql.Connection connection;
    public static Statement statement;

    public static java.sql.Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/fastfood",
                    "postgres", "thais123");
            statement = connection.createStatement();
            if (connection != null) {
                System.out.println("Banco de dados conectado!");
            } else {
                System.out.println("Conexão com o banco de dados falhou!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

