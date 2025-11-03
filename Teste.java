package dao;

// src/Teste.java
import util.ConexaoDB;
import java.sql.Connection;

public class Teste {
    public static void main(String[] args) {
        Connection c = ConexaoDB.getConexao1();
        System.out.println(c != null ? "OK" : "FALHOU");
    }
}