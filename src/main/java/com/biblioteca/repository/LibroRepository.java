package com.biblioteca.repository;

import com.biblioteca.model.Libro;
import java.util.List;

public interface LibroRepository {

    void createLibro(Libro libro);
    List<Libro> selectAllLibro(); // esto sera el SELECT * FROM libros OJO! enunciado no mostrar descripcion en la info GLOBAL
    Libro selectLibroById(Integer id_libro); // esto el SELECT FROM libros WHERE id=?
    List<Libro> selectLibroByTitle(String Titulo); // esto el SELECT FROM libros WHERE titulo=? busqueda flexible mas de un resultado
    void updateLibro(Libro libro);
    void deleteLibroById(Integer id_libro);
    void deleteLibroByTitle(String Titulo);
    
}
