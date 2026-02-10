    package com.biblioteca;



import com.biblioteca.controller.LibroController;
import com.biblioteca.repository.AutorRepository;
import com.biblioteca.repository.AutorRepositoryImp;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.LibroRepositoryImp;
import com.biblioteca.view.BookView;


public class App {
    public static void main(String[] args) {

AutorRepository autorRepository = new AutorRepositoryImp();
        LibroRepository libroRepository = new LibroRepositoryImp(); 

        LibroController Controller = new LibroController(autorRepository, libroRepository);

        BookView view = new BookView(Controller);

        view.start();
    }
}
