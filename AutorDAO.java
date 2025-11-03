package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Trabalho.Autor;
import util.ConexaoDB;

public class AutorDAO {

    public void inserir(Autor autor) throws SQLException {
        String sql = "INSERT INTO Autores (nome, nacionalidade) VALUES (?, ?)";
        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, autor.getNome());
            ps.setString(2, autor.getNacionalidade());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                autor.setId_autor(rs.getInt(1));
            }
        }
    }

    public List<Autor> listar() throws SQLException {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM Autores";
        try (Connection conn = ConexaoDB.getConexao1();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Autor a = new Autor();
                a.setId_autor(rs.getInt("id_autor"));
                a.setNome(rs.getString("nome"));
                a.setNacionalidade(rs.getString("nacionalidade"));
                autores.add(a);
            }
        }
        return autores;
    }

    public void atualizar(Autor autor) throws SQLException {
        String sql = "UPDATE Autores SET nome = ?, nacionalidade = ? WHERE id_autor = ?";
        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, autor.getNome());
            ps.setString(2, autor.getNacionalidade());
            ps.setInt(3, autor.getId_autor());
            ps.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        // Verifica se tem livros
        String check = "SELECT COUNT(*) FROM Livros WHERE id_autor = ?";
        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(check)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                throw new SQLException("Não pode remover: autor tem livros associados!");
            }
        }

        String sql = "DELETE FROM Autores WHERE id_autor = ?";
        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}