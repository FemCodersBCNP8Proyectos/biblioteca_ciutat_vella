package com.biblioteca.model;

public class Autor {

    private Long id_autor;
    private String nombre;



    public Autor(String nombre) {
        this.nombre = nombre;
    }


    public Long getId_autor() {
        return this.id_autor;
    }

    public void setId_autor(Long id_autor) {
        this.id_autor = id_autor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
