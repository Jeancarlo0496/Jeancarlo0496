package com.example.alura_challenge.LiterAluraChallenge.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String nacimiento,
        @JsonAlias("death_year") String fallecimiento){

    @Override
    public String nombre() {
        return nombre;
    }

    public String nacimiento() {
        return String.valueOf(Integer.parseInt(nacimiento));
    }

    public String fallecimiento() {
        return String.valueOf(Integer.parseInt(fallecimiento));
    }
}