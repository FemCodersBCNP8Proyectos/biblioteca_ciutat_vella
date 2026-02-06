package com.biblioteca.controller;

import com.biblioteca.model.Autor;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.LibroRepository;

public class LibroController {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public LibroController(AutorRepository autorRepository){
        this.libroRepository = libroRepository;
    }

    public void createAutor(Autor autor){
        autorRepository.createAutor(autor);
    }

}
