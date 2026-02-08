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

public class AutorRepositoryImp implements AutorRepository {

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
        List<String> librosAutor = findAutorLibros(id_autor);
        if (!librosAutor.isEmpty()){
            System.out.println(Colors.RED + "ACCIÓN DENEGADA: No se puede eliminar el autor (ID: " + id_autor + ")." + Colors.RESET);
            System.out.println("Esta autor tiene los siguientes libros vinculados: " + librosAutor);
            System.out.println("Si estás seguro de querer eliminar el autor y todas sus obras registradas en el inventario selecciona X");
            return;
        }
        String sql = "DELETE FROM autores WHERE id_autor = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_autor);
            int rows = st.executeUpdate();
            if (rows > 0) {
                System.out.println(Colors.GREEN + "Autor con ID " + id_autor + " eliminado correctamente."+ Colors.RESET);
            } else {
                System.out.println(Colors.YELLOW + "No se pudo eliminar: No existe ningún autor con ID " + id_autor + Colors.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al ejecutar el borrado: " + e.getMessage() + Colors.RESET);
        }
    }

    @Override
    public void deleteAutorByName(String nombre) {
        String sql = "SELECT id_autor FROM autores WHERE nombre = ?";
        Integer idAutor = null;

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, nombre);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                idAutor = rs.getInt("id_autor");
            }else{
                System.out.println(Colors.RED + "El autor '" + nombre + "' no existe." + Colors.RESET);
                return;
            }
            List<String> librosAutor = findAutorLibros(idAutor);
            if(!librosAutor.isEmpty()){
                System.out.println(Colors.RED + "BORRADO DENEGADO: " + nombre + " tiene estos libros: " + librosAutor + " vinculados." + Colors.RESET);
                return;
            }

            String sqlD = "DELETE FROM autores WHERE nombre = ?";

            try( PreparedStatement stD = conn.prepareStatement(sqlD)){
                stD.setString(1, nombre);
                stD.executeUpdate();
                System.out.println(Colors.GREEN + "Autor '" + nombre + "' eliminado de la Base de Datos." + Colors.RESET);
            }
        } catch (SQLException e){
            throw new RuntimeException(Colors.RED + "Error: " + e.getMessage() + Colors.RESET);
        }
    }


    private List<String> findAutorLibros(Integer id_autor){
        List<String> librosAutor = new ArrayList<>();
        String sql = "SELECT l.titulo FROM libros l " +
                 "JOIN autor_libro al ON l.id_libro = al.libro_id " +
                 "WHERE al.autor_id = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_autor);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                librosAutor.add(rs.getString("titulo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al consultar libros del autor: " + e.getMessage() + Colors.RESET);
        }
        return librosAutor;
    }
}
