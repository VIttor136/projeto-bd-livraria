package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Trabalho.Livro;
import util.ConexaoDB;

public class LivroDAO {

    public void inserir(Livro livro) throws SQLException {
        String sql = "INSERT INTO Livros (titulo, ano_publicacao, id_autor) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getAnoPublicacao());
            ps.setInt(3, livro.getIdAutor());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                livro.setIdLivro(rs.getInt(1));
            }
        }
    }

    public List<Livro> listar() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM Livros";
        try (Connection conn = ConexaoDB.getConexao1();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livro l = new Livro();
                l.setIdLivro(rs.getInt("id_livro"));
                l.setTitulo(rs.getString("titulo"));
                l.setAnoPublicacao(rs.getInt("ano_publicacao"));
                l.setIdAutor(rs.getInt("id_autor"));
                livros.add(l);
            }
        }
        return livros;
    }

    public void atualizar(Livro livro) throws SQLException {
        String sql = "UPDATE Livros SET titulo = ?, ano_publicacao = ?, id_autor = ? WHERE id_livro = ?";
        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getAnoPublicacao());
            ps.setInt(3, livro.getIdAutor());
            ps.setInt(4, livro.getIdLivro());
            ps.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM Livros WHERE id_livro = ?";
        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}