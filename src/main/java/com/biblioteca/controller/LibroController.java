package com.biblioteca.controller;

import com.biblioteca.model.Autor;
import com.biblioteca.model.Genero;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.LibroRepository;
import java.util.List;
import java.util.ArrayList;


public class LibroController {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;


    public LibroController(AutorRepository autorRepository, LibroRepository libroRepository){
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }


    public void createAutor(Autor autor){
        try {
            autorRepository.createAutor(autor);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createLibro(Libro libro){
        try {
            libroRepository.createLibro(libro);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Libro> selectAllLibro() {
        try {
            return libroRepository.selectAllLibro();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public Libro selectLibroById(Integer id_libro) {
        try {
            return libroRepository.selectLibroById(id_libro);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Libro> selectLibroByTitle(String titulo){
        try {
            return libroRepository.selectLibroByTitle(titulo);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Libro> selectLibroByAuthor(String nombre){
        try {
            return libroRepository.selectLibroByAuthor(nombre);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }  
    }

    public List<Libro> selectLibroByGenre(Genero genero){
        try {
            return libroRepository.selectLibroByGenre(genero);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void deleteLibroById(Integer id_libro){
        try {
            libroRepository.deleteLibroById(id_libro);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}