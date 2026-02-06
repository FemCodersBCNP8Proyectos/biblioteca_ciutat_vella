package com.biblioteca.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.biblioteca.model.Autor;
import com.config.DBManager;

public class AutorRepositoryImpl implements AutorRepository {

    @Override
    public void createAutor(Autor autor) {
        String sql = "INSERT INTO autores (nombre) VALUES (?)";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, autor.getNombre());
            st.executeUpdate();
            System.out.println("Autor guardado correctamente en la base de datos.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar el autor: " + e.getMessage());
        }
    }

    @Override
    public List<Autor> selectAllAutor() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT id_autor, nombre FROM autores ORDER BY nombre ASC";

        try (Connection conn = DBManager.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Autor autor = new Autor(rs.getInt("id_autor"), rs.getString("nombre"));
                autores.add(autor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar autores: " + e.getMessage());
        }
        return autores;
    }

    @Override
    public Autor selectAutorById(Integer id_autor) {
        String sql = "SELECT * FROM autores WHERE id_autor = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_autor);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Autor(rs.getInt("id_autor"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Autor selectAutorByName(String nombre) {
        String sql = "SELECT * FROM autores WHERE nombre ILIKE ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, nombre);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Autor(rs.getInt("id_autor"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por nombre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateAutor(Autor autor) {
        String sql = "UPDATE autores SET nombre = ? WHERE id_autor = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, autor.getNombre());
            st.setInt(2, autor.getId_autor());
            int rows = st.executeUpdate();
            if (rows > 0) {
                System.out.println("Autor actualizado con éxito.");
            } else {
                System.out.println("No se encontró ningún autor con ID: " + autor.getId_autor());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public void deleteAutorById(Integer id_autor) { 
        String sql = "DELETE FROM autores WHERE id_autor = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_autor);
            int rows = st.executeUpdate();
            if (rows > 0) {
                System.out.println("Autor con ID " + id_autor + " eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar: No existe ningún autor con ID " + id_autor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar: " + e.getMessage());
        }
    }

    @Override
    public void deleteAutorByName(String nombre) {
        String sql = "DELETE FROM autores WHERE nombre = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, nombre);
            int rows = st.executeUpdate();
            if (rows > 0) {
                System.out.println("Autor '" + nombre + "' eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar: El autor '" + nombre + "' no existe.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar: " + e.getMessage());
        }
    }
}
