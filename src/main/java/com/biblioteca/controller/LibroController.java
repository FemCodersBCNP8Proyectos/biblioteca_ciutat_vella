package com.biblioteca.controller;

import java.util.ArrayList;
import java.util.List;

import com.biblioteca.model.Autor;
import com.biblioteca.model.Libro;
import com.biblioteca.model.Genero;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.LibroRepository;

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

}