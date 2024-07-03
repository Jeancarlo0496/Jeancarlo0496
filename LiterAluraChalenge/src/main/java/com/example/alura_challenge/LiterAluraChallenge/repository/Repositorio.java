package com.example.alura_challenge.LiterAluraChallenge.repository;

import com.example.alura_challenge.LiterAluraChallenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Repositorio extends JpaRepository<Libro, Long> {
    List<Libro> findAll();

    @Query(value = "SELECT autores FROM libros", nativeQuery = true)
    List<String> findAllAutores();

    @Query("""
        SELECT l
        FROM Libro l
        WHERE (l.fecha_nacimiento <= :anioFin AND (l.fecha_muerte IS NULL OR l.fecha_muerte >= :anioInicio))
    """)
    List<Libro> buscarAutoresVivosEnRango(@Param("anioInicio") int anioInicio, @Param("anioFin") int anioFin);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> buscarLibrosPorIdioma(@Param("idioma") String idioma);
}