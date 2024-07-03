package com.example.alura_challenge.LiterAluraChallenge.Service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
