package com.biblioteca.controller;

import com.biblioteca.model.Autor;
import com.biblioteca.model.Genero;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.LibroRepository;
import java.util.List;


public class LibroController {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;


    public LibroController(AutorRepository autorRepository, LibroRepository libroRepository){
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void createAutor(Autor autor){
        autorRepository.createAutor(autor);
    }

    public void createLibro(Libro libro){
        libroRepository.createLibro(libro);
    }

    public List<Libro> selectAllLibro() {
        return libroRepository.selectAllLibro();
    }

    public Libro selectLibroById(Integer id_libro) {
        return libroRepository.selectLibroById(id_libro);
    }

    public List<Libro> selectLibroByTitle(String titulo){
        return libroRepository.selectLibroByTitle(titulo);
    }

    public List<Libro> selectLibroByAuthor(String nombre){
        return libroRepository.selectLibroByAuthor(nombre);
    }

    public List<Libro> selectLibroByGenre(Genero genero){
        return libroRepository.selectLibroByGenre(genero);
    }

    public void deleteLibroById(Integer id_libro){
        libroRepository.deleteLibroById(id_libro);
    }
}