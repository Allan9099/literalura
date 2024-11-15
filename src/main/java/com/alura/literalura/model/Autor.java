package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer añoDeNacimiento;
    private Integer añoDeMuerte;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.añoDeNacimiento = datosAutor.añoDeNacimiento();
        this.añoDeMuerte = datosAutor.añoDeMuerte();
    }

    @Override
    public String toString() {
        return "**********************************************" +
                "\nNombre: " + nombre +
                "\nAño de nacimiento: " + añoDeNacimiento +
                "\nAño de muerte: " + añoDeMuerte +
                "\n**********************************************\n";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAñoDeNacimiento() {
        return añoDeNacimiento;
    }

    public void setAñoDeNacimiento(Integer añoDeNacimiento) {
        this.añoDeNacimiento = añoDeNacimiento;
    }

    public Integer getAñoDeMuerte() {
        return añoDeMuerte;
    }

    public void setAñoDeMuerte(Integer añoDeMuerte) {
        this.añoDeMuerte = añoDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}