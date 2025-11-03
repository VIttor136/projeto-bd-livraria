// src/util/ConexaoDB.java
package util;

import java.sql.*;

public class ConexaoDB {
    private static final String URL = "jdbc:mysql://localhost:3306/livraria_db?" +
        "useSSL=false&" +
        "serverTimezone=UTC&" +
        "allowPublicKeyRetrieval=true";  // ESSA LINHA É OBRIGATÓRIA!

    private static final String USER = "root";
    private static final String PASS = "Vittor2526!@"; // COLOQUE A SENHA DO WORKBENCH!

    public static Connection getConexao1() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("CONECTADO COM SUCESSO!");
            return conn;
        } catch (SQLException e) {
            System.err.println("ERRO: " + e.getMessage());
            return null;
        }
    }
}