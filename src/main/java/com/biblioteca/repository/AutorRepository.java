package com.biblioteca.repository;


import com.biblioteca.model.Autor;
import java.util.List;

public interface AutorRepository {

    void createAutor(Autor autor);
    List<Autor> selectAutorAll(); // esto sera el SELECT * FROM autores
    Autor selectAutorById(Integer id); // esto el SELECT FROM autores WHERE id=?
    void updateAutor(Autor autor);
    void deleteAutor(Integer id);

}
