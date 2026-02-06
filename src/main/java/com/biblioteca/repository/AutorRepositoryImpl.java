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
            throw new RuntimeException ("Error al guardar el autor: " + e.getMessage());
        }
    }

    @Override
    public List<Autor> selectAutorAll() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT id_autor, nombre FROM autores ORDER BY id_autor"; // ordenamos por Id o podemos tambien hacer la query de ordenar ASC por nombre? Decidir en equipo

        try (Connection conn = DBManager.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Autor autor = new Autor(rs.getInt("id_autor"), rs.getString("nombre"));
                autores.add(autor);
            }
        } catch (SQLException e) {
             throw new RuntimeException ("Error al listar autores: " + e.getMessage());
        }
        return autores;
    }

    @Override
    public Autor selectAutorById(Integer id) {
        String sql = "SELECT * FROM autores WHERE id_autor = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Autor(rs.getInt("id_autor"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

            int filasAfectadas = st.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Autor actualizado con éxito.");
            }

        } catch (SQLException e) {
             throw new RuntimeException ("Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public void deleteAutor(Integer id) { // decidimos si queremos eliminar por id o por nombre o por ambos y pasamos parametros.
        String sql = "DELETE FROM autores WHERE id_autor = ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("Autor eliminado.");

        } catch (SQLException e) {
             throw new RuntimeException ("Error al eliminar: " + e.getMessage());
        }
    }

}
