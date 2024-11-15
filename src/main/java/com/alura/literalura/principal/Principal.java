package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.Utilidades;

import java.time.LocalDate;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> libro;
    private List<Autor> autor;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    ---(Ingrese el número de su opción deseada)---
                    
                    1 - Buscar libro por título
                    2 - Lista de libros registrados
                    3 - Lista de autores registrados
                    4 - Buscar autores vivos por año
                    5 - Buscar por idioma
                                  
                    0 - Salir
                    ----------------------------------------------
                    """;
            System.out.println(menu);
            opcion = Utilidades.leerEnteroValidado(teclado, 0, 5);
            teclado.nextLine();

            switch (opcion) {
                case 0:
                    System.out.println("Finalizando ejecución del programa.\n" +
                            "Aplicación para \"Challenge: LiterAlura\" de Alura Latam, realizado por Allan Farías.\n");
                    break;
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosBD();
                    break;
                case 3:
                    listarAutoresBD();
                    break;
                case 4:
                    listarAutoresVivosPorAño();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("----------------------------------------------" +
                "\nIngrese el título del libro:");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));

        DatosRespuestaAPI respuestaAPI = conversor.obtenerDatos(json, DatosRespuestaAPI.class);

        if (respuestaAPI.resultados() != null && !respuestaAPI.resultados().isEmpty()) {
            return respuestaAPI.resultados().get(0);
        } else {
            System.out.println("----------------------------------------------" +
                    "\nNo se encontraron resultados para el libro solicitado.");
            return null;
        }
    }

    private void buscarLibroWeb() {
        DatosLibro datosLibro = getDatosLibro();
        if (datosLibro == null) {
            return;
        }

        // Procesar solo el primer autor, en caso de haber autores
        Autor autor = null;
        if (datosLibro.autores() != null && !datosLibro.autores().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autores().get(0);

            if (datosAutor.nombre() != null && !datosAutor.nombre().isBlank()) {
                // Buscar autor en la base de datos
                autor = autorRepositorio.findByNombre(datosAutor.nombre())
                        .orElseGet(() -> {
                            // Crear y guardar autor si no existía antes
                            Autor nuevoAutor = new Autor(datosAutor);
                            autorRepositorio.save(nuevoAutor);
                            return nuevoAutor;
                        });
            }
        }

        // Crear objeto Libro y verificar si ya existe
        Libro libro = new Libro(datosLibro, autor);
        if (libroRepositorio.findByTitulo(libro.getTitulo()).isPresent()) {
            System.out.println("----------------------------------------------" +
                    "\nEl libro ya está registrado en la base de datos.");
            return;
        }

        libroRepositorio.save(libro);
        System.out.println("----------------------------------------------" +
                "\nLibro guardado:\n" + libro);
    }

    private void listarLibrosBD() {
        libro = libroRepositorio.findAll();
        libro.forEach(System.out::println);
    }

    private void listarAutoresBD() {
        autor = autorRepositorio.findAll();
        autor.forEach(System.out::println);
    }

    private void listarAutoresVivosPorAño() {
        System.out.println("Ingrese el año:");
        int año = Utilidades.leerEnteroValidado(teclado, -2000, LocalDate.now().getYear());

        List<Autor> autoresVivosEseAño = autorRepositorio.findAutoresVivosEnAño(año);

        if (autoresVivosEseAño.isEmpty()) {
            System.out.println("----------------------------------------------" +
                    "\nNo se encontraron autores vivos en el año " + año + ".");
        } else {
            System.out.println("----------------------------------------------" +
                    "\nAutores vivos en el año " + año + ":\n");
            for (Autor autor : autoresVivosEseAño) {
                if(autor.getAñoDeMuerte() == año) {
                    System.out.println("- " + autor.getNombre() + " (" + año + " es el año de muerte de este autor.)");
                } else {
                    System.out.println("- " + autor.getNombre());
                }
            }
        }
    }

    public void buscarLibrosPorIdioma() {
        System.out.println("""
             
             --------(Nota: estas son sugerencias.)--------
             (Ingresa "Idiomas" para ver los de tus libros)
             
             ES - Español
             EN - Inglés
             JA - Japonés
             PT - Portugués
             
             ----(Ingresa el código del idioma deseado)----""");

        var opcionCodigo = Utilidades.leerCodigoIdioma(teclado);

        if (opcionCodigo.equalsIgnoreCase("Idiomas")) {
            List<String> idiomasDisponibles = libroRepositorio.findDistinctIdiomas();

            if (idiomasDisponibles.isEmpty()) {
                System.out.println("No hay idiomas disponibles actualmente.");
            } else {
                System.out.println("------------(Idiomas disponibles)-------------\n");
                idiomasDisponibles.forEach(idioma -> System.out.println("- " + idioma.toUpperCase()));
                buscarLibrosPorIdioma();
            }
        } else {
            List<Libro> librosEnRespectivoIdioma = libroRepositorio.findByIdioma(opcionCodigo);
            if (librosEnRespectivoIdioma.isEmpty()) {
                System.out.println("----------------------------------------------\n" +
                        "\nNo se encontraron libros en este idioma. (" + opcionCodigo.toUpperCase() + ")");
            } else {
                System.out.println("----------------------------------------------" +
                        "\nLibros en " + opcionCodigo.toUpperCase() + ":");
                librosEnRespectivoIdioma.forEach(System.out::println);
            }
        }
    }
}