package Trabalho;

public class Autor {
    private int id_autor;
    private String nome;
    private String nacionalidade;

    // Construtor vazio
    public Autor() {}

    // Construtor com parâmetros
    public Autor(String nome, String nacionalidade) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    // GETTERS E SETTERS
    public int getId_autor() {
        return id_autor; // ← CORRETO: retorna o campo!
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return "Autor [ID=" + id_autor + ", Nome=" + nome + ", Nacionalidade=" + nacionalidade + "]";
    }
}