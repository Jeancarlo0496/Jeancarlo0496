package com.example.alura_challenge.LiterAluraChallenge.Principal;

import com.example.alura_challenge.LiterAluraChallenge.Service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {
    @Autowired
    private LibroService libroService;


    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("""
                             ***************************
                             ***MI BIBLIOTECA VIRTUAL***
                             ***************************
                    *********************************************""");
            System.out.println("""
                    1) Buscar Libro Por Titulo
                    2) Listar Libros Guardados
                    3) Listar Autores Registrados
                    4) Listar Autores Vivos en Determinado Año
                    5) Listar Libros Por Idioma
                    6) Salir
                    """);
            System.out.println("Digite la Opcion Que Desea Realizar: ");
            opcion = scanner.nextInt();
            try {

                switch (opcion) {
                    case 1:
                        libroService.buscarLibro();
                        break;
                    case 2:
                        libroService.listarLibrosGuardados();
                        break;
                    case 3:
                        libroService.listarAutores();
                        break;
                    case 4:
                        libroService.autoresVivos();
                        break;
                    case 5:
                        libroService.librosIdioma();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Debe ingresar una opcion valida");
                }
            } catch (Exception e) {
                System.out.println("Debe Unicamente Digitar Números que Correspondan al Menu");
            }
        } while (opcion != 6);
        scanner.close();
        System.exit(0);
    }

}



