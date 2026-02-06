package com.biblioteca.repository;


import com.biblioteca.model.Autor;
import java.util.List;

public interface AutorRepository {

    void createAutor(Autor autor);
    List<Autor> selectAllAutor();
    Autor selectAutorById(Integer id_autor);
    Autor selectAutorByName(String nombre);
    void updateAutor(Autor autor);
    void deleteAutorById(Integer id_autor);
    void deleteAutorByName(String nombre);
}
