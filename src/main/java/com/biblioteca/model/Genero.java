package com.biblioteca.model;

public enum Genero {
    FICCION("ficcion"), 
    POLICIACA("policiaca"), 
    ROMANTICA("romantica"), 
    FANTASIA("fantasia"), 
    TERROR("terror"), 
    AVENTURAS("aventuras"), 
    PSICOLOGIA("psicologia"), 
    PROGRAMACION("programacion"), 
    INFANTIL("infantil"), 
    HISTORIA("historia"),
    NOVELA("novela");


    private final String generoDB;

    Genero (String value){
        this.generoDB = value;
    }


    public String getGeneroDb(){
        return this.generoDB;
    }


    public static Genero findGenero(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        for (Genero g : Genero.values()) {
            if (g.name().equalsIgnoreCase(input.trim())) {
                return g;
            }
        }
        return null;
    }

}
