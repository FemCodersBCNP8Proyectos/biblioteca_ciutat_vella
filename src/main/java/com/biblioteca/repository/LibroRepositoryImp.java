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
                Libro libro = new Libro(
                        rs.getInt("id_libro"),
                        rs.getString("titulo"),
                        rs.getString("isbn"));

                String autores = rs.getString("autores");
                if (autores != null) {
                    String[] arrayAutores = autores.split(", ");
                    for (String name : arrayAutores) {
                        libro.addAutor(new Autor(name));
                    }
                }
                String generos = rs.getString("generos");
                if (generos != null) {
                    String[] arrayGeneros = generos.split(", ");
                    for (String genero : arrayGeneros) {
                        libro.addGenero(Genero.findGenero(genero));
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
        String sql = "SELECT * FROM libros WHERE id_libro = ?";
        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id_libro);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    Libro libro = new Libro(
                            rs.getInt("id_libro"),
                            rs.getString("titulo"),
                            rs.getString("descripcion"),
                            rs.getString("isbn"));
                    fillAutores(libro, conn);
                    fillGeneros(libro, conn);
                    return libro;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error al buscar por ID: " + e.getMessage() + Colors.RESET);
        }
        return null;
    }

    @Override
    public List<Libro> selectLibroByTitle(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT id_libro FROM libros WHERE titulo ILIKE ?";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + titulo.trim() + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libros.add(selectLibroById(rs.getInt("id_libro")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error en búsqueda por título: " + e.getMessage() + Colors.RESET);
        }
        return libros;
    }

    @Override
    public List<Libro> selectLibroByAuthor(String nombre) {
        List<Libro> libros = new ArrayList<>();
        String sql = """
                SELECT DISTINCT al.libro_id
                FROM autor_libro al
                JOIN autores a ON al.autor_id = a.id_autor
                WHERE a.nombre ILIKE ?""";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + nombre.trim() + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libros.add(selectLibroById(rs.getInt("libro_id")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error buscando por autor: " + e.getMessage() + Colors.RESET);
        }
        return libros;
    }

    @Override
    public List<Libro> selectLibroByGenre(Genero genero) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT libro_id FROM libro_generos WHERE genero = ?::genero";

        try (Connection conn = DBManager.getConnection();
                PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, genero.getGeneroDb());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libros.add(selectLibroById(rs.getInt("libro_id")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(Colors.RED + "Error buscando por género: " + e.getMessage() + Colors.RESET);
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
    public void deleteLibroByTitle(String titulo) {
        String sql = "SELECT id_libro FROM libros WHERE titulo = ?";

        try (Connection conn = DBManager.getConnection();
            PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, titulo.trim());
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()){
                    int id = rs.getInt("id_libro");
                    deleteLibroById(id);
                } else {
                    System.out.println(Colors.YELLOW + "No se encontró ningún libro con el título: " + titulo + Colors.RESET);
                }
            }
        } catch (SQLException e) {
        throw new RuntimeException(Colors.RED + "Error al intentar localizar el libro por título: " + e.getMessage() + Colors.RESET);
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

    private void fillAutores(Libro libro, Connection conn) throws SQLException {
        String sql = """
                SELECT a.id_autor, a.nombre
                FROM autores a
                JOIN autor_libro al ON a.id_autor = al.autor_id
                WHERE al.libro_id = ?""";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, libro.getId_libro());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libro.addAutor(new Autor(rs.getInt("id_autor"), rs.getString("nombre")));
                }
            }
        }
    }

    private void fillGeneros(Libro libro, Connection conn) throws SQLException {
        String sql = "SELECT genero FROM libro_generos WHERE libro_id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, libro.getId_libro());
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    libro.addGenero(Genero.findGenero(rs.getString("genero")));
                }
            }
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

}
