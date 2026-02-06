package com.biblioteca.model;

// import java.util.ArrayList;
import java.util.List;

public class Libro {

    private Integer id_libro;
    private String titulo;
    private String descripcion;
    private String isbn;
    // private List<Autor> autores = new ArrayList<>();
    // private List<Genero> generos = new ArrayList<>();


    public Libro( String titulo, String descripcion, String isbn, List<Autor> autores, List<Genero> generos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.isbn = isbn;
        // this.autores = autores;
        // this.generos = generos;
    }



    public Integer getId_libro() {
        return this.id_libro;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // public List<Autor> getAutores() {
    //     return this.autores;
    // }

    // public void setAutores(List<Autor> autores) {
    //     this.autores = autores;
    // }

    // public List<Genero> getGeneros() {
    //     return this.generos;
    // }

    // public void setGeneros(List<Genero> generos) {
    //     this.generos = generos;
    // }


}