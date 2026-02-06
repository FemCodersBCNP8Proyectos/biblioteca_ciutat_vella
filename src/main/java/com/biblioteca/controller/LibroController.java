package com.biblioteca.controller;

import com.biblioteca.model.Autor;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.LibroRepository;

public class LibroController {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public LibroController(AutorRepository autorRepository, LibroRepository libroRepository){
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

}
