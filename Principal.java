package Trabalho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dao.AutorDAO;
import dao.LivroDAO;
import util.ConexaoDB;

public class Principal {

    private static final AutorDAO autorDAO = new AutorDAO();
    private static final LivroDAO livroDAO = new LivroDAO();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        splashScreen();
        contarRegistros();

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> relatorios();
                case 2 -> inserirRegistro();
                case 3 -> removerRegistro();
                case 4 -> atualizarRegistro();
                case 5 -> System.out.println("Sistema finalizado com sucesso!");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 5);

        sc.close();
    }

    // ====================== SPLASH SCREEN ======================
    private static void splashScreen() {
        System.out.println("=".repeat(60));
        System.out.println("    SISTEMA DE GERENCIAMENTO DE LIVRARIA");
        System.out.println("    Disciplina: Banco de Dados");
        System.out.println("    Professor: Howard Roatti");
        System.out.println("    Grupo:");
        System.out.println("    Vittor");
        System.out.println("=".repeat(60));
    }

    // ====================== CONTAGEM DE REGISTROS ======================
    private static void contarRegistros() {
        try (Connection conn = ConexaoDB.getConexao1()) {
            String sqlAutor = "SELECT COUNT(1) AS total_autores FROM Autores";
            String sqlLivro = "SELECT COUNT(1) AS total_livros FROM Livros";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sqlAutor);
            rs.next();
            int totalAutores = rs.getInt("total_autores");

            rs = stmt.executeQuery(sqlLivro);
            rs.next();
            int totalLivros = rs.getInt("total_livros");

            System.out.println("\nTabelas carregadas:");
            System.out.println("→ Autores: " + totalAutores + " registro(s)");
            System.out.println("→ Livros: " + totalLivros + " registro(s)\n");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
    }

    // ====================== MENU ======================
    private static void exibirMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1 - Relatórios");
        System.out.println("2 - Inserir registros");
        System.out.println("3 - Remover registros");
        System.out.println("4 - Atualizar registros");
        System.out.println("5 - Sair");
    }

    // ====================== INSERIR ======================
    private static void inserirRegistro() {
        System.out.println("\n[INSERIR] 1 - Autor | 2 - Livro");
        int tipo = lerInteiro("Escolha: ");
        sc.nextLine(); // limpar buffer

        if (tipo == 1) {
            System.out.print("Nome do autor: ");
            String nome = sc.nextLine().trim();
            System.out.print("Nacionalidade: ");
            String nac = sc.nextLine().trim();

            Autor autor = new Autor();
            autor.setNome(nome);
            autor.setNacionalidade(nac);

            try {
                autorDAO.inserir(autor);
                System.out.println("AUTOR INSERIDO COM SUCESSO! ID = " + autor.getId_autor());
            } catch (SQLException e) {
                System.out.println("Erro ao inserir autor: " + e.getMessage());
            }

        } else if (tipo == 2) {
            System.out.print("Título do livro: ");
            String titulo = sc.nextLine().trim();

            int ano = lerInteiro("Ano de publicação: ");
            int idAutor = lerInteiro("ID do Livro: ");

            Livro livro = new Livro();
            livro.setTitulo(titulo);
            livro.setAnoPublicacao(ano);
            livro.setIdAutor(idAutor);

            try {
                livroDAO.inserir(livro);
                System.out.println("LIVRO INSERIDO COM SUCESSO!");
            } catch (SQLException e) {
                if (e.getMessage().contains("foreign key")) {
                    System.out.println("ERRO: Autor com ID " + idAutor + " não existe! Insira o autor primeiro.");
                } else {
                    System.out.println("Erro ao inserir livro: " + e.getMessage());
                }
            }
        } else {
            System.out.println("Opção inválida!");
        }
    }

    // ====================== REMOVER ======================
    private static void removerRegistro() {
        System.out.println("\n[REMOVER] 1 - Autor | 2 - Livro");
        int tipo = lerInteiro("Escolha: ");
        sc.nextLine();

        if (tipo == 1) {
            int id = lerInteiro("ID do autor: ");
            try {
                autorDAO.remover(id);
                System.out.println("Autor removido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao remover: " + e.getMessage());
            }
        } else if (tipo == 2) {
            int id = lerInteiro("ID do livro: ");
            try {
                livroDAO.remover(id);
                System.out.println("Livro removido com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro ao remover: " + e.getMessage());
            }
        }
    }

    // ====================== ATUALIZAR ======================
    private static void atualizarRegistro() {
        System.out.println("\n[ATUALIZAR] 1 - Autor | 2 - Livro");
        int tipo = lerInteiro("Escolha: ");
        sc.nextLine();

        if (tipo == 1) {
            int id = lerInteiro("ID do autor: ");
            sc.nextLine();
            System.out.print("Novo nome: ");
            String nome = sc.nextLine().trim();
            System.out.print("Nova nacionalidade: ");
            String nac = sc.nextLine().trim();

            Autor autor = new Autor();
            autor.setId_autor(id);
            autor.setNome(nome);
            autor.setNacionalidade(nac);

            try {
                autorDAO.atualizar(autor);
                System.out.println("Autor atualizado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else if (tipo == 2) {
            int id = lerInteiro("ID do livro: ");
            sc.nextLine();
            System.out.print("Novo título: ");
            String titulo = sc.nextLine().trim();
            int ano = lerInteiro("Novo ano: ");
            int idAutor = lerInteiro("Novo ID do autor: ");

            Livro livro = new Livro();
            livro.setIdLivro(id);
            livro.setTitulo(titulo);
            livro.setAnoPublicacao(ano);
            livro.setIdAutor(idAutor);

            try {
                livroDAO.atualizar(livro);
                System.out.println("Livro atualizado com sucesso!");
            } catch (SQLException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ====================== RELATÓRIOS ======================
    private static void relatorios() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RELATÓRIO 1: LIVROS POR AUTOR (GROUP BY)");
        System.out.println("=".repeat(50));

        String sql1 = """
            SELECT a.nome, COUNT(l.id_livro) AS total_livros
            FROM Autores a
            LEFT JOIN Livros l ON a.id_autor = l.id_autor
            GROUP BY a.id_autor, a.nome
            """;

        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql1);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf("%-30s → %d livro(s)%n",
                        rs.getString("nome"), rs.getInt("total_livros"));
            }
        } catch (SQLException e) {
            System.out.println("Erro no relatório: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("       RELATÓRIO 2: LIVROS COM NOME DO AUTOR (JOIN)");
        System.out.println("=".repeat(50));

        String sql2 = """
            SELECT l.titulo, a.nome AS autor_nome
            FROM Livros l
            INNER JOIN Autores a ON l.id_autor = a.id_autor
            ORDER BY l.titulo
            """;

        try (Connection conn = ConexaoDB.getConexao1();
             PreparedStatement ps = conn.prepareStatement(sql2);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf("• %s - Autor: %s%n",
                        rs.getString("titulo"), rs.getString("autor_nome"));
            }
        } catch (SQLException e) {
            System.out.println("Erro no relatório: " + e.getMessage());
        }
    }

    // ====================== MÉTODO AUXILIAR: LER INTEIRO COM SEGURANÇA ======================
    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = sc.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("ERRO: Digite apenas números!");
            }
        }
    }
}