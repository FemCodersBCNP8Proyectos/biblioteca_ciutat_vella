package com.biblioteca.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import com.biblioteca.model.Libro;
import com.biblioteca.model.Autor;
import com.config.DBManager;

public class LibroRepositoryImp implements LibroRepository{

    @Override
    public void createLibro(Libro libro, , ) {
        
        String sql = "INSERT INTO libros(titulo,descripcion,isbn) VALUES (?,?,?)";

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql
        )) {
            st.setString(1, libro.getTitulo());
            st.setString(2, libro.getDescripcion());
            st.setString(3, libro.getIsbn());


            throw new RuntimeErrorException ("Error al insertar el libro" + e.getMessage());
        }


    } 

}
