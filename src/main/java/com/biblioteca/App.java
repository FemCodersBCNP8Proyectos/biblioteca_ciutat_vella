    package com.biblioteca;

import java.util.List;

import com.biblioteca.model.Autor;
import com.biblioteca.model.Colors;
import com.biblioteca.model.Genero;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.LibroRepositoryImp;

public class App {
    public static void main(String[] args) {

        LibroRepository libroRepository = new LibroRepositoryImp();

        Libro l1 = new Libro("La reina de los condenados", "El vampiro Lestat...", "978-84-9759-679-7");
        l1.setAutores(List.of(new Autor("Anne Rice")));
        l1.setGeneros(List.of(Genero.FANTASIA));
        saveLibro(libroRepository, l1);

        Libro l2 = new Libro("Un trabajo muy sucio", "Humor negro...", "978-84-9800-712-1");
        l2.setAutores(List.of(new Autor("Christopher Moore")));
        l2.setGeneros(List.of(Genero.FANTASIA));
        saveLibro(libroRepository, l2);

        Libro l3 = new Libro("El color de la magia", "Un mundo plano...", "978-84-9759-679-4");
        l3.setAutores(List.of(new Autor("Terry Pratchett")));
        l3.setGeneros(List.of(Genero.TERROR));
        saveLibro(libroRepository, l3);

        Libro l4 = new Libro("Ladrón del tiempo.", "Tic Tac Tic Tac", "978-84-9908-703-0");
        l4.setAutores(List.of(new Autor("Terry Pratchett")));
        l4.setGeneros(List.of(Genero.TERROR));
        saveLibro(libroRepository, l4);

        System.out.println(Colors.BOLD + Colors.CYAN + "\n             GESTION DE INVENTARIO BIBLIOTECA CIUTAT VELLA\n"
                + Colors.RESET);
        System.out.println(Colors.BOLD + "ID    | TÍTULO                         | AUTOR/ES                  | ISBN"
                + Colors.RESET);
        System.out.println("---------------------------------------------------------------------------------------");

        List<Libro> inventario = libroRepository.selectAllLibro();

        for (Libro libro : inventario) {
            String nombresAutores = "";
            for (Autor autor : libro.getAutores()) {
                nombresAutores += autor.getNombre() + " ";
            }

            System.out.printf("%-5d | %-30.30s | %-25.25s | %-18s %n",
                    libro.getId_libro(),
                    libro.getTitulo(),
                    nombresAutores,
                    libro.getIsbn());
        }
        System.out.println("---------------------------------------------------------------------------------------");

    }

    private static void saveLibro(LibroRepository repo, Libro libro) {
        try {
            repo.createLibro(libro);
        } catch (Exception e) {
            System.out.println(Colors.RED + "La prueba falló por: " + e.getMessage()+ Colors.RESET);
        }
    }
}
