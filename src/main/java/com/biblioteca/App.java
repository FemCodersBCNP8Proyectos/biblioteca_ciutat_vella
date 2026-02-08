package com.biblioteca;-

import java.util.ArrayList;
import java.util.List;
import com.biblioteca.model.Autor;
import com.biblioteca.model.Genero;
import com.biblioteca.model.Libro;
import com.biblioteca.repository.LibroRepository;
import com.biblioteca.repository.LibroRepositoryImp;

public class App 
{
    public static void main( String[] args )
    {

    LibroRepository libroRepo = new LibroRepositoryImp();

    Autor autorPrueba = new Autor("Anne Rice");
    List<Autor> listaAutores = new ArrayList<>();
    listaAutores.add(autorPrueba);

    List<Genero> listaGeneros = new ArrayList<>();
    listaGeneros.add(Genero.FANTASIA); -

    Libro nuevoLibro = new Libro("La reina de los condenados", "El vampiro Lestat en su epoca RockStar.", "978-84-9759-679-7");
    nuevoLibro.setAutores(listaAutores);
    nuevoLibro.setGeneros(listaGeneros);

    try {
        libroRepo.createLibro(nuevoLibro);
    } catch (Exception e) {
        System.out.println("La prueba falló por: " + e.getMessage());
    }

    }
}
