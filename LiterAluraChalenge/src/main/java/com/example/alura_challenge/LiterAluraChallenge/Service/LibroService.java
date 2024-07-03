package com.example.alura_challenge.LiterAluraChallenge.Service;

import com.example.alura_challenge.LiterAluraChallenge.model.Datos;
import com.example.alura_challenge.LiterAluraChallenge.model.DatosAutor;
import com.example.alura_challenge.LiterAluraChallenge.model.DatosLibros;
import com.example.alura_challenge.LiterAluraChallenge.model.Libro;
import com.example.alura_challenge.LiterAluraChallenge.repository.Repositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class LibroService {
    Scanner scanner = new Scanner(System.in);
    private Repositorio libroRepository;
    private final ConsumirAPI consumirAPI = new ConsumirAPI();
    private final ConvierteDatos conversor = new ConvierteDatos();

    @Autowired
    public LibroService(Repositorio libroRepository) {
        this.libroRepository = libroRepository;

    }

    public void listarLibrosGuardados() {
        List<Libro> librosGuardados = libroRepository.findAll();
        for (Libro libro : librosGuardados) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.print("Autores: " + libro.getAutores());
            System.out.println();
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getTotalDescargas());
            System.out.println();
        }
        System.out.println("Digite 0 para volver al inicio");
        scanner.nextLine();
    }

    public void listarAutores() {
        List<String> autoresGuardados = libroRepository.findAllAutores();
        System.out.println("Autores registrados:");
        for (String autor : autoresGuardados) {
            System.out.println(autor);
        }
        System.out.println("Digite 0 para volver al inicio");
        scanner.nextLine();
    }

    public void buscarLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        Scanner teclado = new Scanner(System.in);
        var tituloLibro = teclado.nextLine();
        var json = consumirAPI.obtenerDatos("https://gutendex.com/books/?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("""
                    **************************
                    *****Libro Encontrado*****
                    **************************
                    """);
            System.out.println("Titulo: " + libroBuscado.get().titulo());
            System.out.println("Autor: " + libroBuscado.get().autores().get(0).nombre());
            System.out.println("Fecha de nacimiento: " + libroBuscado.get().autores().get(0).nacimiento());
            System.out.println("Fecha de fallecimiento: " + libroBuscado.get().autores().get(0).fallecimiento());
            System.out.print("Idioma: ");
            for (String idioma : libroBuscado.get().idioma()) {
                if (Objects.equals(idioma, "es")) {
                    System.out.println("Español");
                }
                if (Objects.equals(idioma, "en")) {
                    System.out.println("Inglés");

                }
            }
            System.out.println();
            System.out.println("Cantidad de Descargas: " + libroBuscado.get().totalDescargas());
            System.out.println(" ");
            System.out.println("Para guardar el libro encontrado digite 1 o 0 para continuar");
            var guardarLibro = teclado.nextInt();
            if (guardarLibro == 1) {
                Libro libro = new Libro();
                libro.setTitulo(libroBuscado.get().titulo());

                List<DatosAutor> autores = libroBuscado.get().getAutores();
                List<String> nombresAutores = new ArrayList<>();

                for (DatosAutor autor : autores) {
                    nombresAutores.add(autor.nombre());
                }
                String autoresString = String.join(", ", nombresAutores);
                libro.setAutores(autoresString);

                libro.setFecha_nacimiento(Integer.parseInt(libroBuscado.get().autores().get(0).nacimiento()));
                libro.setFecha_muerte(Integer.parseInt(libroBuscado.get().autores().get(0).fallecimiento()));
                Map<String, String> idiomas = new HashMap<>();
                idiomas.put("[es]", "español");
                idiomas.put("[en]", "ingles");
                String idiomaAbreviado = libroBuscado.get().idioma().toString();
                String idiomaCompleto = idiomas.getOrDefault(idiomaAbreviado, idiomaAbreviado);
                libro.setIdioma(idiomaCompleto);
                libro.setTotalDescargas(libroBuscado.get().totalDescargas());

                libroRepository.save(libro);
                System.out.println("Digite 0 para volver al inicio");
                scanner.nextInt();
            } else {
                System.out.println("Libro no encontrado");
                System.out.println("Digite 0 para volver al inicio");
                scanner.nextLine();

            }
        }
    }

    public void autoresVivos() {
        int anyoIni;
        int anyoFin;
        System.out.println("Digite los años en los que desea realizar la busqueda:");
        System.out.println("Desde el año: ");
        anyoIni = scanner.nextInt();
        System.out.println("Hasta el año: ");
        anyoFin = scanner.nextInt();

        List<Libro> autoresVivos = libroRepository.buscarAutoresVivosEnRango(anyoIni, anyoFin);
        System.out.println("Autores vivos entre " + anyoIni + " y " + anyoFin + ":");

        for (Libro libro : autoresVivos) {
            System.out.println(libro.getAutores());
        }
        System.out.println("Digite 0 para volver al inicio");
        scanner.nextLine();
    }

    public void librosIdioma() {
        System.out.println("Escriba el idioma que desea buscar:");
        String idioma = scanner.nextLine();

        List<Libro> libros = libroRepository.buscarLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma + ".");
        } else {
            System.out.println("Libros en el idioma " + idioma + " :");
            for (Libro libro : libros) {
                System.out.println(libro.getTitulo());
            }
        }

        System.out.println("Digite 0 para volver al inicio");
        scanner.nextLine();
    }
}
