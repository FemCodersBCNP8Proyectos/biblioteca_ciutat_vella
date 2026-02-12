package com.biblioteca.view;

import com.biblioteca.controller.LibroController;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Colors;
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
          hasInteracted = true;
          break;
        case 3:
          System.out.println(Colors.YELLOW + "\n⚠️  Función 'Añadir' aún no implementada.\n" + Colors.RESET);
          hasInteracted = true;
          break;
        case 4:
          System.out.println(Colors.YELLOW + "\n⚠️  Función 'Editar' aún no implementada.\n" + Colors.RESET);
          hasInteracted = true;
          break;
        case 5:
          System.out.println(Colors.YELLOW + "\n⚠️  Función 'Eliminar' aún no implementada.\n" + Colors.RESET);
          hasInteracted = true;
          break;
        case 6: {
          keepGoing = false;
          goodbyeMessage();
          break;
        }
        default:
          System.out.println(Colors.RED + "\n❌ Opción inválida. Introduce un número del 1 al 6.\n" + Colors.RESET);
          break;
      }
    }
  }

  private void welcomeMessage() {
    System.out.println(Colors.BOLD + Colors.CYAN + "\n📚      BIENVENIDO/A A BIBLIOTECA CIUTAT VELLA \n"
        + Colors.RESET);
  }

  private void showMainMenu(boolean hasSeenInventory, boolean hasInteracted) {
    String questionMenu = (!hasInteracted) ? "¿Qué quieres hacer hoy?" : "¿Qué quieres hacer ahora?";
    System.out.println(Colors.BOLD + "\n" + questionMenu + "\n" + Colors.RESET);

    if (!hasSeenInventory) {
      System.out.println("[1] 📖 Ver todo el inventario");
    }
    System.out.println("[2] 🔍 Buscar un libro");
    System.out.println("[3] ➕ Añadir un nuevo libro");
    System.out.println("[4] ✏️  Editar un libro");
    System.out.println("[5] 🗑️  Eliminar un libro");
    System.out.println("[6] 🚪 Salir");
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
    System.out.println(Colors.BOLD + String.format("%-3s | %-32.32s | %-20.20s | %-12.12s | %-13s",
        "ID", "TÍTULO", "AUTOR/ES", "GÉNERO/S", "ISBN") + Colors.RESET);
    System.out
        .println("--------------------------------------------------------------------------------------------------");

    List<Libro> inventario = controller.selectAllLibro();

    for (Libro libro : inventario) {
      String nombresAutores = "";
      for (Autor autor : libro.getAutores()) {
        nombresAutores += autor.getNombre() + ", ";
      }
      if (nombresAutores.endsWith(", "))
        nombresAutores = nombresAutores.substring(0, nombresAutores.length() - 2);
      String generos = "";
      for (com.biblioteca.model.Genero g : libro.getGeneros()) {
        generos += g.name() + " ";
      }
      System.out.printf("%-3d | %-32.32s | %-20.20s | %-12.12s | %-13s %n",
          libro.getId_libro(),
          libro.getTitulo(),
          nombresAutores,
          generos,
          libro.getIsbn());
    }
    System.out
        .println("--------------------------------------------------------------------------------------------------");
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
          System.out.println("searchByTitle()");
          break;
        case 2:
          System.out.println("searchByAuthor()");
          break;
        case 3:
          System.out.println("searchByGenre()");
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

  private void searchByTitle(){
    System.out.println("Introduce el titulo");
    String title = scanner.nextLine().trim();
  
  while(title.isEmpty()){
      System.out.println(Colors.RED + "\n❌ El titulo no puede estar vacio.\n" + Colors.RESET);
        System.out.println("Introduce el titulo");
        title = scanner.nextLine().trim(); 

         List<Libro> libros = controller.selectLibroByTitle(title);

         if(llibros.isEmpty()) {
          System.out.println(Colors.YELLOW + "\n⚠️  No se encontro ningun libro .\n" + Colors.RESET);
         }  else{
          System.out.println(Colors.GREEN + "\n✅ Se han encontrado  " + libros + Colors.RESET);
         }
         
  

        
        

      

  }
}
