<!-- [MermaidChart: 2e50b154-e043-483d-9fb7-9acc25ba894a] -->
<!-- [MermaidChart: 6e8637a3-f07f-4fe6-bb3a-e8611d241252] -->

# Biblioteca Ciutat Vella (Java)

## Descripción del proyecto

Proyecto desarrollado en PgAdmin como parte del bootcamp de Factoria F5, aplicando buenas prácticas de programación arquitectura MVC y patrón de diseño repository.

Sistema de gestión de biblioteca desarrollado en Java que permite la administración completa de un inventario de libros mediante operaciones CRUD (Crear, Leer, Actualizar, Eliminar). Este proyecto forma parte de la modernización de la biblioteca del barrio, facilitando la gestión y organización del catálogo de libros.

## Objetivos

La biblioteca de ciutat vella necesita modernizarse y mantener todos sus libros organizados en un programa web. Un inventario actualizado facilitará la gestión y permitirá prestar mejores servicios.

La administradora necesita:

- Añadir libros al catálogo
- Actualizar información de libros existentes
- Eliminar libros del inventario
- Visualizar el catálogo completo
- Buscar libros por diferentes atributos (título, autor, género)

## Tecnologías y herramientas

### Tecnologías

- **Java 21** - Lenguaje de programación principal
- **PostgreSQL** - Base de datos
- **Maven** - Gestión de dependencias y estructura del proyecto
- **JUnit** - Framework para tests unitarios
- **Mockito** - Framework para mocking en tests

### Herramientas

- Visual Studio Code
- Git & GitHub
- Jira (Atlassian)
- pgAdmin 4 (Gestión de datos)

## Arquitectura del proyecto

El proyecto implementa una arquitectura MVC (Model-View-Controller) con el patrón Repository para el acceso a datos:

```
📦src
 ┣ 📂main
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┣ 📂biblioteca
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┗ 📜LibroController.java
 ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Autor.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Colors.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜Genero.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜Libro.java
 ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┣ 📜AutorRepository.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜AutorRepositoryImp.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📜LibroRepository.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜LibroRepositoryImp.java
 ┃ ┃ ┃ ┃ ┣ 📂view
 ┃ ┃ ┃ ┃ ┃ ┗ 📜BookView.java
 ┃ ┃ ┃ ┃ ┗ 📜App.java
 ┃ ┃ ┃ ┗ 📂config
 ┃ ┃ ┃ ┃ ┗ 📜DBManager.java
 ┣ 📂resources
 ┃ ┣ 📜FlowChart.mmd
 ┃ ┣ 📜proyecto_biblioteca_ciutat_vella_3FN.sql
 ┃ ┗ 📜UserFlow1.mmd
 ┗ 📂test
 ┃ ┗ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂biblioteca
 ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┗ 📜AutorRepositoryImpTest.java
 ┃ ┃ ┃ ┃ ┗ 📜AppTest.java
🔒.env
.gitignore
pom.xml
README.md
```

## Modelo de Base de Datos

### Entidades Principales

**Libros**

- ID (PK)
- Título
- Descripción (máx. 200 caracteres)
- ISBN

**Autores**

- ID (PK)
- Nombre

**Géneros Literarios**

- ENUM Género ('ficción', 'policíaca', 'romántica', 'fantasía', 'terror', 'psicología', 'programación', 'infantil', 'historia')

### Relaciones

- Un libro puede tener varios autores (relación N:M)
- Un libro puede pertenecer a varios géneros (relación N:M)

La base de datos está normalizada siguiendo la 3FN para evitar redundancia y mantener la integridad de los datos y agiliza la carga.

## Requisitos Funcionales Cumplidos

El sistema permite al usuario:

- **Ver todos los libros** - Lista completa del catálogo (sin descripción)
- **Añadir un libro** - Registrar nuevo libro con todos sus datos
- **Editar un libro** - Actualizar información existente
- **Eliminar un libro** - Borrar del inventario
- **Buscar por título** - Muestra todos los campos del libro
- **Buscar por autor** - Muestra todos los campos de los libros del autor
- **Buscar por género** - Muestra todos los campos excepto descripción

## Instalación y Configuración

### Prerrequisitos

- Java JDK 21 o superior
- PostgreSQL instalado y configurado 42.7.9
- Maven 21
- JUnit 4.13.2
- Mockito 5.14.2
- DOTENV 3.2.0

### Guía de instalación

```bash
git clone https://github.com/FemCodersBCNP8Proyectos/biblioteca_ciutat_vella.git
```

1. Descargar archivo `Biblioteca_Ciutat_Vella.sql`
2. Ejecutarlo en pgAdmin4
3. Actualizar archivo `.env` con su propio Usuario y Contraseña PostgreSQL
4. Arrancar `App.java`

## Test

El proyecto incluye test unitarios realizados con Mockito y JUnit:

- **createAutorSavesSuccessfully()** - Verifica la nueva inserción del autor en la base de datos
- **selectAllAutorSuccessfully()** - Interroga a la base de datos y muestra la recuperación del dato

Auto repository

## Equipo de Desarrollo

Este proyecto ha sido desarrollado utilizando metodologías ágiles.

### Metodología

- **Scrum** - Sprints semanales
- **MOB** - Programación en grupo
- **Code Review** - Revisión de código entre compañeras

### Proyecto desarrollado en equipo por:

- **Sukaina Hadani** — https://github.com/sukisu91-alt
- **Charlotte Doulcet** — https://github.com/Charlottedoulcet
- **Guadalupe Peña** — https://github.com/AdaXana
- **Jennifer Ceballos** — https://github.com/JenCeballos
