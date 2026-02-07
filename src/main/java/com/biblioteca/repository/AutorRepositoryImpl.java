package com.biblioteca.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Colors;
import com.config.DBManager;

public class AutorRepositoryImpl implements AutorRepository {

    @Override
    public void createAutor(Autor autor) {
        String sql = "INSERT INTO autores (nombre) VALUES (?)";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, autor.getNombre());
            st.executeUpdate();
            System.out.println(Colors.GREEN +"Autor guardado correctamente en la base de datos."+ Colors.RESET);
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al guardar el autor: " + e.getMessage()+ Colors.RESET);
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
            throw new RuntimeException(Colors.RED +"Error al listar autores: " + e.getMessage()+Colors.RESET);
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
            throw new RuntimeException(Colors.RED + "Error al buscar por id: " + e.getMessage()+ Colors.RESET);
        }
        return null;
    }

    @Override
    public List<Autor> selectAutorByName(String nombre) {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autores WHERE nombre ILIKE ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1,"%" + nombre.trim() + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    autores.add(new Autor(
                        rs.getInt("id_autor"),
                        rs.getString("nombre")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al buscar autor/es por nombre: " + e.getMessage()+ Colors.RESET);
        }
        return autores;
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
                System.out.println(Colors.GREEN + "Autor actualizado con éxito." + Colors.RESET);
            } else {
                System.out.println(Colors.RED + "No se encontró ningún autor con ID: " + autor.getId_autor() + Colors.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al actualizar: " + e.getMessage()+ Colors.RESET);
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
                System.out.println(Colors.GREEN + "Autor con ID " + id_autor + " eliminado correctamente."+ Colors.RESET);
            } else {
                System.out.println(Colors.RED + "No se pudo eliminar: No existe ningún autor con ID " + id_autor + Colors.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al eliminar: " + e.getMessage() + Colors.RESET);
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
                System.out.println(Colors.GREEN + "Autor '" + nombre + "' eliminado correctamente." + Colors.RESET);
            } else {
                System.out.println(Colors.RED + "No se pudo eliminar: El autor '" + nombre + "' no existe." + Colors.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al eliminar: " + e.getMessage() + Colors.RESET);
        }
    }
}
