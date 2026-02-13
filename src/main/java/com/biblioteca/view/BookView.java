package com.biblioteca.view;

import com.biblioteca.controller.LibroController;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Colors;
import com.biblioteca.model.Genero;
import com.biblioteca.model.Libro;
import java.util.List;
import java.util.Scanner;

public class BookView {

    private Scanner scanner;
    private LibroController controller;

    public BookView(LibroController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        welcomeMessage();
        boolean keepGoing = true;
        boolean hasSeenInventory = false;
        boolean hasInteracted = false;

        while (keepGoing) {
            showMainMenu(hasSeenInventory, hasInteracted);
            int inputMenu = chosenOption();
            switch (inputMenu) {
                case 1:
                    showAllBooks();
                    hasSeenInventory = true;
                    hasInteracted = true;
                    break;
                case 2:
                    searchBook();
                    hasSeenInventory = false;
                    hasInteracted = true;
                    break;
                case 3:
                    System.out.println(Colors.YELLOW + "\n⚠️  Función 'Añadir' aún no implementada.\n" + Colors.RESET);
                    hasSeenInventory = false;
                    hasInteracted = true;
                    break;
                case 4:
                    System.out.println(Colors.YELLOW + "\n⚠️  Función 'Editar' aún no implementada.\n" + Colors.RESET);
                    hasSeenInventory = false;
                    hasInteracted = true;
                    break;
                case 5:
                    System.out
                            .println(Colors.YELLOW + "\n⚠️  Función 'Eliminar' aún no implementada.\n" + Colors.RESET);
                    hasSeenInventory = false;
                    hasInteracted = true;
                    break;
                case 6: {
                    keepGoing = false;
                    goodbyeMessage();
                    break;
                }
            }
        }
    }

    private void welcomeMessage() {
        System.out.println(Colors.BOLD + Colors.CYAN + "\n📚      BIENVENIDO/A A BIBLIOTECA CIUTAT VELLA \n"
                + Colors.RESET);
    }

    private void showMainMenu(boolean hasSeenInventory, boolean hasInteracted) {
        String questionMenu = (!hasInteracted) ? Colors.BOLD + Colors.PURPLE +"¿Qué quieres hacer hoy?" : "¿Qué quieres hacer ahora?" + Colors.RESET;
        System.out.println(Colors.BOLD + "\n" + questionMenu + "\n" + Colors.RESET);

        if (!hasSeenInventory) {
            System.out.println(Colors.BOLD + Colors.PURPLE + "[1]" + Colors.RESET + " 📖 Ver todo el inventario");
        }
        System.out.println(Colors.BOLD + Colors.PURPLE +"[2]" + Colors.RESET + "  🔍 Buscar un libro");
        System.out.println(Colors.BOLD + Colors.PURPLE +"[3]" + Colors.RESET + " ➕ Añadir un nuevo libro");
        System.out.println(Colors.BOLD + Colors.PURPLE +"[4]" + Colors.RESET + " ✏️  Editar un libro");
        System.out.println(Colors.BOLD + Colors.PURPLE +"[5]" + Colors.RESET + " 🗑️  Eliminar un libro");
        System.out.println(Colors.BOLD + Colors.PURPLE +"[6]" + Colors.RESET + " 🚪 Salir");
        System.out.print("\n➤ Introduce tu opción (1-6): ");
    }

    private int chosenOption() {
        String inputMenu = scanner.nextLine().trim();
        try {
            return Integer.parseInt(inputMenu);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void goodbyeMessage() {
        System.out.println(Colors.BOLD + Colors.CYAN + "\n👋 ¡HASTA PRONTO!\n"
                + Colors.RESET);
        scanner.close();
    }

    private void showAllBooks() {
        System.out.println(Colors.BOLD + Colors.CYAN + "\n📖      INVENTARIO COMPLETO - BIBLIOTECA CIUTAT VELLA \n"
                + Colors.RESET);
        List<Libro> inventario = controller.selectAllLibro();
        printTable(inventario);
        System.out.println(Colors.GREEN + "\n✅ Total de libros: " + inventario.size() + Colors.RESET);
    }


    private void searchBook() {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println(Colors.BOLD + Colors.CYAN + "\n🔍 BUSCAR LIBRO\n" + Colors.RESET);
            System.out.println("¿Cómo quieres buscar?");
            System.out.println("[1] Por título");
            System.out.println("[2] Por autor");
            System.out.println("[3] Por género");
            System.out.println("[4] Volver al menú principal");
            System.out.print("\n➤ Introduce tu opción (1-4): ");

            int inputSubMenu = chosenOption();

            switch (inputSubMenu) {
                case 1:
                    searchByTitle();
                    break;
                case 2:
                    searchByAuthor();
                    break;
                case 3:
                    searchByGenre();
                    break;
                case 4:
                    backToMainMenu = true;
                    break;
                default:
                    System.out.println(Colors.RED + "\n❌ Opción inválida.\n" + Colors.RESET);
                    break;
            }
        }
    }

    private void searchByTitle() {
        String inputTitle = "";
        while (inputTitle.isEmpty()) {
            System.out.println("Introduce el titulo: ");
            inputTitle = scanner.nextLine().trim();
            if (inputTitle.isEmpty()) {
                System.out.println(Colors.RED + "\n❌ El titulo no puede estar vacio.\n" + Colors.RESET);
            }
        }
        List<Libro> libros = controller.selectLibroByTitle(inputTitle);
        if (libros.isEmpty()) {
            System.out.println(Colors.YELLOW + "\n⚠️ No se encontro ningun libro .\n" +
                    Colors.RESET);
        } else {
            System.out.println(Colors.GREEN + "\n✅ Total de libros encontrados: " + libros.size() + Colors.RESET);
            for (Libro l : libros) {
                printBookDetails(l);
            }
        }
    }

    private void searchByAuthor() {
        String inputAuthor = "";
        while (inputAuthor.isEmpty()) {
            System.out.println("Introduce el autor: ");
            inputAuthor = scanner.nextLine().trim();
            if (inputAuthor.isEmpty()) {
                System.out.println(Colors.RED + "\n❌ El autor no puede estar vacio.\n" + Colors.RESET);
            }
        }
        List<Libro> libros = controller.selectLibroByAuthor(inputAuthor);
        if (libros.isEmpty()) {
            System.out.println(Colors.YELLOW + "\n⚠️ No se encontro ningun libro de este autor .\n" +
                    Colors.RESET);
        } else {
            System.out.println(Colors.GREEN + "\n✅ Total de libros encontrados: " + libros.size() + Colors.RESET);
            for (Libro l : libros) {
                printBookDetails(l);
            }
        }
    }

    private void searchByGenre() {
        String inputGenre = "";
        Genero genreMatch = null;
        while (genreMatch == null) {
            System.out.println("\nGéneros disponibles:");
            for (Genero g : Genero.values()) {
                System.out.print("[" + g.getGeneroDb() + "] ");
            }
            System.out.println("\nIntroduce un genero: ");
            inputGenre = scanner.nextLine().trim();
            genreMatch = Genero.findGenero(inputGenre);
            if (genreMatch == null) {
                System.out.println(
                        Colors.RED + "\n❌ Genero no reconocido. Por favor, elige uno de la lista.\n" + Colors.RESET);
            }
        }
        List<Libro> libros = controller.selectLibroByGenre(genreMatch);
        if (libros.isEmpty()) {
        System.out.println(Colors.YELLOW + "\n⚠️ No se encontraron libros de este género.\n" + Colors.RESET);
    } else {
        System.out.println(Colors.GREEN + "\n✅ Total de libros encontrados: " + libros.size() + Colors.RESET);
        printTable(libros);
    }
    }

    private void printTable(List<Libro> libros) {
        if (libros.isEmpty()) {
            System.out.println(Colors.YELLOW + "\n⚠️  No hay libros para mostrar.\n" + Colors.RESET);
            return;
        }
        System.out.println(Colors.BOLD + String.format("%-3s | %-32.32s | %-20.20s | %-12.12s | %-13s",
                "ID", "TÍTULO", "AUTOR/ES", "GÉNERO/S", "ISBN") + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.CYAN +"--------------------------------------------------------------------------------------------------" + Colors.RESET);
        for (Libro libro : libros) {
            String nombresAutores = "";
            for (Autor autor : libro.getAutores()) {
                nombresAutores += autor.getNombre() + ", ";
            }
            if (nombresAutores.endsWith(", "))
                nombresAutores = nombresAutores.substring(0, nombresAutores.length() - 2);
            String generos = "";
            for (Genero g : libro.getGeneros()) {
                generos += g.name() + " ";
            }
            System.out.printf("%-3d | %-32.32s | %-20.20s | %-12.12s | %-13s %n",
                    libro.getId_libro(),
                    libro.getTitulo(),
                    nombresAutores,
                    generos,
                    libro.getIsbn());
        }
        System.out.println(Colors.BOLD + Colors.CYAN +"--------------------------------------------------------------------------------------------------" + Colors.RESET);
    }

    private void printBookDetails(Libro libro) {
        System.out.println(Colors.BOLD + Colors.CYAN + "\n--------------------------------------------------------------------------------------------------\n" + Colors.RESET);
        System.out.println(Colors.BOLD + "TÍTULO:      " + Colors.RESET + Colors.YELLOW + libro.getTitulo() + Colors.RESET);
        String autores = "";
        for (Autor a : libro.getAutores()) {
            autores += a.getNombre() + ", ";
        }
        if (autores.endsWith(", "))
            autores = autores.substring(0, autores.length() - 2);
        System.out.println(Colors.BOLD + "AUTOR/ES:    " + Colors.RESET + autores);
        String generos = "";
        for (Genero g : libro.getGeneros()) {
            generos += g.name() + " ";
        }
        System.out.println(Colors.BOLD + Colors.CYAN + "GÉNERO/S:    " + Colors.RESET + generos);
        System.out.println(Colors.BOLD + Colors.CYAN +  "ISBN:        " + Colors.RESET + libro.getIsbn());
        System.out.println(Colors.BOLD + Colors.CYAN + "DESCRIPCIÓN: " + Colors.RESET + libro.getDescripcion() + Colors.RESET);
        System.out.println(Colors.CYAN + Colors.CYAN + "\n--------------------------------------------------------------------------------------------------\n\n" + Colors.RESET);
    }
}