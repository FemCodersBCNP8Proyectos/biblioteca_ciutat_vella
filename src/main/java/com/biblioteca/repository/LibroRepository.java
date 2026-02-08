package com.biblioteca.repository;

import com.biblioteca.model.Autor;
import com.biblioteca.model.Genero;
import com.biblioteca.model.Libro;

import java.util.List;

public interface LibroRepository {

    void createLibro(Libro libro);
    List<Libro> selectAllLibro();
    Libro selectLibroById(Integer id_libro);
    List<Libro> selectLibroByTitle(String titulo);
    List<Libro> selectLibroByAutor(String nombre);
    List<Libro> selectLibroByGenre(Genero genero);
    void updateLibro(Libro libro);
    void deleteLibroByTitle(String titulo);
    void deleteLibroById(Integer id_libro);
}
