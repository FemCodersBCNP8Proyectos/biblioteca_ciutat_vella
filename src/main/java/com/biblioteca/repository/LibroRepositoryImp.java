package com.biblioteca.repository;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.biblioteca.model.Libro;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Genero;
import com.config.DBManager;

public class LibroRepositoryImp implements LibroRepository {

    @Override
    public void createLibro(Libro libro) {
    }

    @Override
    public List<Libro> selectAllLibro() {
        return new ArrayList<>();
    }

    @Override
    public Libro selectLibroById(Integer id_libro) {
        return null;
    }

    @Override
    public Libro selectLibroByTitle(String titulo) {
        return null;
    }

    @Override
    public void updateLibro(Libro libro) {
    }

    @Override
    public void deleteLibroById(Integer id_libro) {
    }

    @Override
    public void deleteLibroByTitle(String titulo) {
    }
    
}
