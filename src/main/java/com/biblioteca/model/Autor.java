package com.biblioteca.model;

public class Autor {

    private Integer id_autor;
    private String nombre;


    public Autor() {}

    public Autor(String nombre) {
        this.nombre = nombre;
    }

    public Autor(Integer id_autor, String nombre) {
        this.id_autor = id_autor;
        this.nombre = nombre;
    }


    public Integer getId_autor() {
        return this.id_autor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
