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

  public BookView(LibroController controller){
    this.controller = controller;
    this.scanner = new Scanner(System.in);
  }

  public void start(){

    welcomeMessage();
    boolean keepGoing = true;
    int lastInput = 0; 

    while(keepGoing){
      showMainMenu(lastInput);
      int inputMenu = chosenOption();

      switch (inputMenu) {
        case 1: showAllBooks();
                lastInput = 1; 
        break;
        case 2: System.out.println(Colors.YELLOW + "\n⚠️  Función 'Buscar' aún no implementada.\n" + Colors.RESET);
                lastInput = 2; 
        break;
        case 3: System.out.println(Colors.YELLOW + "\n⚠️  Función 'Añadir' aún no implementada.\n" + Colors.RESET);
                lastInput = 3; 
        break;
        case 4: System.out.println(Colors.YELLOW + "\n⚠️  Función 'Editar' aún no implementada.\n" + Colors.RESET);
                lastInput = 4; 
        break;
        case 5: System.out.println(Colors.YELLOW + "\n⚠️  Función 'Eliminar' aún no implementada.\n" + Colors.RESET);
                lastInput = 5; 
        break;
        case 6: {
          keepGoing = false;
          goodbyeMessage();
          break;
        }
        default: System.out.println(Colors.RED + "\n❌ Opción inválida. Introduce un número del 1 al 6.\n" + Colors.RESET);
        break;
      }
    }
  }

  private void welcomeMessage () {
    System.out.println(Colors.BOLD + Colors.CYAN + "\n📚      BIENVENIDO/A A BIBLIOTECA CIUTAT VELLA \n"
                + Colors.RESET);
  }

  private void showMainMenu (int lastInput){
    String questionMenu = (lastInput == 0 ) ? "¿Qué quieres hacer hoy?" : "¿Qué quieres hacer ahora?";
    System.out.println(Colors.BOLD + "\n" + questionMenu + "\n" + Colors.RESET);

    String[] inputs = {
      "\n[1] 📖  Ver todo el inventario",
      "\n[2] 🔍  Buscar un libro",
      "\n[3] ➕  Añadir un nuevo libro",
      "\n[4] ✏️   Editar un libro",
      "\n[5] 🗑️   Eliminar un libro",
    };

    for (int input = 0; input < inputs.length; input++){
      if(input + 1 != lastInput){
        System.out.println(inputs[input]);
      }
    }
    System.out.println("\n[6] 🚪  Salir");
    System.out.print("\n➤ Introduce tu opción (1-6): ");
  }


  private int chosenOption (){
    String inputMenu = scanner.nextLine().trim();
    try {
      return Integer.parseInt(inputMenu);
    } catch (NumberFormatException e) {
      return -1;
    }
  }
  private void goodbyeMessage(){
    System.out.println(Colors.BOLD + Colors.CYAN + "\n👋 ¡HASTA PRONTO!\n"
                + Colors.RESET);
    scanner.close();
  }

  private void showAllBooks(){
    System.out.println(Colors.BOLD + Colors.CYAN + "\n📖      INVENTARIO COMPLETO - BIBLIOTECA CIUTAT VELLA \n"
                + Colors.RESET);
    System.out.println(Colors.BOLD + "ID    | TÍTULO                         | AUTOR/ES                  | ISBN"
                + Colors.RESET);
        System.out.println("---------------------------------------------------------------------------------------");

    List<Libro> inventario = controller.selectAllLibro();

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
        System.out.println(Colors.GREEN + "\n✅ Total de libros: " + inventario.size() + Colors.RESET);
  }
}
