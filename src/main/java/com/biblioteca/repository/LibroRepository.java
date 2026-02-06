package com.biblioteca.repository;

import com.biblioteca.model.Libro;
import java.util.List;

public interface LibroRepository {

    void createLibro(Libro libro);
    List<Libro> selectAllLibro(); // esto sera el SELECT * FROM autores
    Libro selectLibroById(Integer id_libro); // esto el SELECT FROM autores WHERE id=?
    Libro selectLibroByTitle(String Titulo); // esto el SELECT FROM autores WHERE titulo=?
    void updateLibro(Libro libro);
    void deleteLibroById(Integer id_libro);
    void deleteLibroByTitle(String Titulo);
    
}
