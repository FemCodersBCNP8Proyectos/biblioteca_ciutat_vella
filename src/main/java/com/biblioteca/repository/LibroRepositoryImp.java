package com.biblioteca.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biblioteca.model.Libro;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Colors;
import com.biblioteca.model.Genero;
import com.config.DBManager;
// import com.biblioteca.repository.AutorRepository;

public class LibroRepositoryImp implements LibroRepository {

    @Override
    public void createLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, descripcion, isbn) VALUES (?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, libro.getTitulo());
            st.setString(2, libro.getDescripcion());
            st.setString(3, libro.getIsbn());
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int idLibro = rs.getInt(1);
                    for (Autor a : libro.getAutores()) {
                        int idAutor = getOrCreateAutor(a);
                        insertAutorLibro(idLibro, idAutor);
                    }
                    for (Genero g : libro.getGeneros()) {
                        insertLibroGenero(idLibro, g);
                    }
                }
            }
            System.out.println(Colors.GREEN + "¡Libro y todas sus relaciones guardadas con éxito!" + Colors.RESET);
        } catch (SQLException e) {
            throw new RuntimeException(
                    Colors.RED + "Error en la creación completa del libro: " + e.getMessage() + Colors.RESET);
        }
    }


    @Override
    public List<Libro> selectAllLibro() {
        List<Libro> inventarioLibros = new ArrayList<>();
        String sql = """
                 SELECT l.id_libro, l.titulo, l.isbn,
                 STRING_AGG(DISTINCT a.nombre, ', ') AS autores,
                 STRING_AGG(DISTINCT lg.genero::text, ', ') AS generos
                 FROM libros l
                 JOIN autor_libro al ON l.id_libro = al.libro_id
                 JOIN autores a ON al.autor_id = a.id_autor
                 JOIN libro_generos lg ON l.id_libro = lg.libro_id
                 GROUP BY l.id_libro, l.titulo, l.isbn
                 ORDER BY l.titulo ASC""";
        try (Connection conn = DBManager.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()){
                Libro libro = new Libro(
                    rs.getInt("id_libro"),
                    rs.getString("titulo"),
                    rs.getString("isbn")
                );

                String autores = rs.getString("autores");
                if(autores!= null){
                    String[] arrayAutores = autores.split(", ");
                    for(String name : arrayAutores){
                        libro.addAutor(new Autor(name));
                    }
                }
                String generos = rs.getString("generos");
                if (generos != null) {
                    String[] arrayGeneros = generos.split(", ");
                    for(String genero : arrayGeneros) {
                        libro.addGenero(Genero.findGenre(genero));
                    }
                }
                inventarioLibros.add(libro); 
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al listar: " + e.getMessage() + Colors.RESET);
        }
        return inventarioLibros;    
    }

    @Override
    public Libro selectLibroById(Integer id_libro) {
        return null;
    }

    @Override
    public List<Libro> selectLibroByTitle(String titulo) {
        return null;
    }

    @Override
    public List<Libro> selectLibroByAutor(String nombre){
        return null;
    }

    @Override
    public List<Libro> selectLibroByGenre(Genero genero){
        return null;
    }

    @Override
    public void updateLibro(Libro libro) {
    }

    @Override
    public void deleteLibroByTitle(String titulo) {
    }

    @Override
    public void deleteLibroById(Integer id_libro) {
        String sql = "DELETE FROM libros WHERE id_libro = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1,id_libro);
            int rows = st.executeUpdate();
            if(rows >0){
                System.out.println(Colors.GREEN + "Libro con ID " + id_libro + " y sus vínculos eliminados correctamente." + Colors.RESET);
            } else {
                System.out.println(Colors.YELLOW + "No se encontró ningún libro con el ID: " + id_libro + Colors.RESET);
            }
        }catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al eliminar el libro: " + e.getMessage() + Colors.RESET);
        }
    }

    



    private int getOrCreateAutor(Autor autor) {
        AutorRepository autorRepository = new AutorRepositoryImp();
        List<Autor> existsAutor = autorRepository.selectAutorByName(autor.getNombre());
        if (!existsAutor.isEmpty()) {
            return existsAutor.get(0).getId_autor();
        } else {
            autorRepository.createAutor(autor);
            List<Autor> newAutor = autorRepository.selectAutorByName(autor.getNombre());
            return newAutor.get(0).getId_autor();
        }
    }

    private void insertAutorLibro( int id_autor, int id_libro) {
        String sql = "INSERT INTO autor_libro (autor_id, libro_id) VALUES (?, ?)";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_autor);
            st.setInt(2, id_libro);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al insertar vinculacion id_libro con id_autor: "
                    + e.getMessage() + Colors.RESET);
        }
    }

    private void insertLibroGenero(int id_libro, Genero genero) {
        String sql = "INSERT INTO libro_generos (libro_id, genero) VALUES (?, ?::genero)";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_libro);
            st.setString(2, genero.getGeneroDb());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al vincular género ENUM: " + e.getMessage() + Colors.RESET);
        }
    }

}
