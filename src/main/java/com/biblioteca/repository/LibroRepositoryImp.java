package com.biblioteca.repository;

import com.biblioteca.model.Autor;
import com.biblioteca.model.Colors;
import com.biblioteca.model.Genero;
import com.biblioteca.model.Libro;
import com.config.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LibroRepositoryImp implements LibroRepository {

    @Override
    public void createLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, descripcion, isbn) VALUES (?, ?, ?)";

        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, libro.getTitulo());
                st.setString(2, libro.getDescripcion());
                st.setString(3, libro.getIsbn());
                st.executeUpdate();
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idLibro = rs.getInt(1);
                        for (Autor a : libro.getAutores()) {
                            int idAutor = getOrCreateAutor(a, conn);
                            insertAutorLibro(idAutor, idLibro, conn);
                        }
                        for (Genero g : libro.getGeneros()) {
                            insertLibroGenero(idLibro, g, conn);
                        }
                    }
                }
                conn.commit();
                System.out.println(Colors.GREEN + "¡Libro y todas sus relaciones guardadas con éxito!" + Colors.RESET);
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(
                        Colors.RED + "Error en la creación completa del libro: " + e.getMessage() + Colors.RESET);
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Fallo crítico de conexión: " + e.getMessage() + Colors.RESET);
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
            while (rs.next()) {
                inventarioLibros.add(mapResultFilter(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al listar: " + e.getMessage() + Colors.RESET);
        }
        return inventarioLibros;
    }

    @Override
    public List<Libro> selectLibroByTitle(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = """
                SELECT l.id_libro, l.titulo, l.descripcion, l.isbn,
                STRING_AGG(DISTINCT a.nombre, ', ') AS autores,
                STRING_AGG(DISTINCT lg.genero::text, ', ') AS generos
                FROM libros l
                JOIN autor_libro al ON l.id_libro = al.libro_id
                JOIN autores a ON al.autor_id = a.id_autor
                JOIN libro_generos lg ON l.id_libro = lg.libro_id
                WHERE l.titulo ILIKE ?
                GROUP BY l.id_libro, l.titulo, l.descripcion, l.isbn""";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + titulo.trim() + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapResultFilter(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return libros;
    }

    @Override
    public List<Libro> selectLibroByAuthor(String nombre) {
        List<Libro> libros = new ArrayList<>();
        String sql = """
                SELECT l.id_libro, l.titulo, l.descripcion, l.isbn,
                STRING_AGG(DISTINCT a.nombre, ', ') AS autores,
                STRING_AGG(DISTINCT lg.genero::text, ', ') AS generos
                FROM libros l
                JOIN autor_libro al ON l.id_libro = al.libro_id
                JOIN autores a ON al.autor_id = a.id_autor
                JOIN libro_generos lg ON l.id_libro = lg.libro_id
                WHERE a.nombre ILIKE ?
                GROUP BY l.id_libro, l.titulo, l.descripcion, l.isbn""";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + nombre.trim() + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapResultFilter(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
        return libros;
    }

    @Override
    public List<Libro> selectLibroByGenre(Genero genero) {
        List<Libro> libros = new ArrayList<>();
        String sql = """
                SELECT l.id_libro, l.titulo, l.descripcion, l.isbn,
                STRING_AGG(DISTINCT a.nombre, ', ') AS autores,
                STRING_AGG(DISTINCT lg.genero::text, ', ') AS generos
                FROM libros l
                JOIN autor_libro al ON l.id_libro = al.libro_id
                JOIN autores a ON al.autor_id = a.id_autor
                JOIN libro_generos lg ON l.id_libro = lg.libro_id
                WHERE lg.genero = ?::genero
                GROUP BY l.id_libro, l.titulo, l.descripcion, l.isbn""";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, genero.getGeneroDb());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libros.add(mapResultFilter(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando por género: " + e.getMessage());
        }
        return libros;
    }

    @Override
    public void updateLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, descripcion = ?, isbn = ? WHERE id_libro = ?";

        try (Connection conn = DBManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.setString(1, libro.getTitulo());
                    st.setString(2, libro.getDescripcion());
                    st.setString(3, libro.getIsbn());
                    st.setInt(4, libro.getId_libro());
                    st.executeUpdate();
                }
                updateAutoresLibro(libro, conn);
                updateGenerosLibro(libro, conn);
                conn.commit();
                System.out.println(Colors.GREEN + "¡Libro y relaciones actualizados!" + Colors.RESET);
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(
                        Colors.RED + "Error en la transacción de actualización: " + e.getMessage() + Colors.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error de conexión: " + e.getMessage() + Colors.RESET);
        }
    }

    @Override
    public Libro selectLibroById(Integer id_libro) {
        String sql = """
                SELECT l.id_libro, l.titulo, l.descripcion, l.isbn,
                STRING_AGG(DISTINCT a.nombre, ', ') AS autores,
                STRING_AGG(DISTINCT lg.genero::text, ', ') AS generos
                FROM libros l
                JOIN autor_libro al ON l.id_libro = al.libro_id
                JOIN autores a ON al.autor_id = a.id_autor
                JOIN libro_generos lg ON l.id_libro = lg.libro_id
                WHERE l.id_libro = ?
                GROUP BY l.id_libro, l.titulo, l.descripcion, l.isbn""";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_libro);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapResultFilter(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteLibroByTitle(String titulo) {
        String sql = "SELECT id_libro FROM libros WHERE titulo = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, titulo.trim());
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_libro");
                    deleteLibroById(id);
                } else {
                    System.out.println(
                            Colors.YELLOW + "No se encontró ningún libro con el título: " + titulo + Colors.RESET);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    Colors.RED + "Error al intentar localizar el libro por título: " + e.getMessage() + Colors.RESET);
        }
    }

    @Override
    public void deleteLibroById(Integer id_libro) {
        String sql = "DELETE FROM libros WHERE id_libro = ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_libro);
            int rows = st.executeUpdate();
            if (rows > 0) {
                System.out.println(Colors.GREEN + "Libro con ID " + id_libro
                        + " y sus vínculos eliminados correctamente." + Colors.RESET);
            } else {
                System.out.println(Colors.YELLOW + "No se encontró ningún libro con el ID: " + id_libro + Colors.RESET);
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al eliminar el libro: " + e.getMessage() + Colors.RESET);
        }
    }

    private int getOrCreateAutor(Autor autor, Connection conn) throws SQLException {
        String sqlSelect = "SELECT id_autor FROM autores WHERE nombre = ?";

        try (PreparedStatement st = conn.prepareStatement(sqlSelect)) {
            st.setString(1, autor.getNombre());
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_autor");
                }
            }
        }
        String sqlInsert = "INSERT INTO autores (nombre) VALUES (?)";

        try (PreparedStatement st = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, autor.getNombre());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException(
                Colors.RED + "No se pudo crear ni encontrar al autor: " + autor.getNombre() + Colors.RESET);
    }

    private void insertAutorLibro(int autor_id, int libro_id, Connection conn) throws SQLException {
        String sql = "INSERT INTO autor_libro (autor_id, libro_id) VALUES (?, ?)";

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, autor_id);
            st.setInt(2, libro_id);
            st.executeUpdate();
        }
    }

    private void insertLibroGenero(int id_libro, Genero genero, Connection conn) throws SQLException {
        String sql = "INSERT INTO libro_generos (libro_id, genero) VALUES (?, ?::genero)";
        
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_libro);
            st.setString(2, genero.getGeneroDb());
            st.executeUpdate();
        }
    }

    private void updateAutoresLibro(Libro libro, Connection conn) throws SQLException {
        String sqlDel = "DELETE FROM autor_libro WHERE libro_id = ?";

        try (PreparedStatement stDel = conn.prepareStatement(sqlDel)) {
            stDel.setInt(1, libro.getId_libro());
            stDel.executeUpdate();
        }
        for (Autor a : libro.getAutores()) {
            int idAutor = getOrCreateAutor(a, conn);
            insertAutorLibro(idAutor, libro.getId_libro(), conn);
        }
    }

    private void updateGenerosLibro(Libro libro, Connection conn) throws SQLException {
        String sqlDel = "DELETE FROM libro_generos WHERE libro_id = ?";

        try (PreparedStatement stDel = conn.prepareStatement(sqlDel)) {
            stDel.setInt(1, libro.getId_libro());
            stDel.executeUpdate();
        }
        for (Genero g : libro.getGeneros()) {
            insertLibroGenero(libro.getId_libro(), g, conn);
        }
    }

    private Libro mapResultFilter(ResultSet rs) throws SQLException {
        Libro libro = new Libro(
                rs.getInt("id_libro"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("isbn"));
        String autoresStr = rs.getString("autores");
        if (autoresStr != null) {
            for (String name : autoresStr.split(", ")) {
                libro.addAutor(new Autor(name));
            }
        }
        String generosStr = rs.getString("generos");
        if (generosStr != null) {
            for (String g : generosStr.split(", ")) {
                libro.addGenero(Genero.findGenero(g));
            }
        }
        return libro;
    }

}
