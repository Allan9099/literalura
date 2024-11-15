package com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor author;
    private String autor;
    private String idioma;
    private Integer numeroDeDescargas;


    public Libro(){}

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo().split(";")[0].trim();
        this.author = autor;
        this.autor = autor != null ? autor.getNombre() : "Autor desconocido";
        this.idioma = datosLibro.idiomas() == null || datosLibro.idiomas().isEmpty() ? "Idioma no especificado" : datosLibro.idiomas().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return "**********************************************" +
                "\nTítulo: " + titulo +
                "\nAutor: " + autor +
                "\nIdioma: " + idioma +
                "\nNúmero de descargas: " + numeroDeDescargas +
                "\n**********************************************\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Autor getAutor() {
        return author;
    }

    public void setAutor(Autor autor) {
        this.author = autor;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAuthor() {
        return author;
    }

    public void setAuthor(Autor author) {
        this.author = author;
        this.autor = author != null ? author.getNombre() : "Autor desconocido"; // Sincroniza el nombre del autor
    }
}