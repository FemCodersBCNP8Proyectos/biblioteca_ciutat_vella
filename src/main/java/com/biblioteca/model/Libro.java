package com.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Libro {

    private Long id_libro;
    private String titulo;
    private String descripcion;
    private String isbn;
    private List<Autor> autores = new ArrayList<>();
    private List<Categoria> categorias = new ArrayList<>();


    public Libro( String titulo, String descripcion, String isbn, List<Autor> autores, List<Categoria> categorias) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.isbn = isbn;
        this.autores = autores;
        this.categorias = categorias;
    }



    public Long getId_libro() {
        return this.id_libro;
    }

    public void setId_libro(Long id_libro) {
        this.id_libro = id_libro;
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

    public List<Autor> getAutores() {
        return this.autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Categoria> getCategorias() {
        return this.categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }


   
}