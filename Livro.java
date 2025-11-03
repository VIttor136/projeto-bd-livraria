package Trabalho;

public class Livro {
    private int idLivro;
    private String titulo;
    private int anoPublicacao;
    private int idAutor;

    public Livro() {}

    public Livro(String titulo, int anoPublicacao, int idAutor) {
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.idAutor = idAutor;
    }

    // Getters e Setters
    public int getIdLivro() { return idLivro; }
    public void setIdLivro(int idLivro) { this.idLivro = idLivro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getAnoPublicacao() { return anoPublicacao; }
    public void setAnoPublicacao(int anoPublicacao) { this.anoPublicacao = anoPublicacao; }

    public int getIdAutor() { return idAutor; }
    public void setIdAutor(int idAutor) { this.idAutor = idAutor; }

    @Override
    public String toString() {
        return "Livro [id=" + idLivro + ", titulo=" + titulo + ", ano=" + anoPublicacao + ", idAutor=" + idAutor + "]";
    }
}




