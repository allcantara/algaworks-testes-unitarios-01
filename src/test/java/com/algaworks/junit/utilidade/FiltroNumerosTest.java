package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FiltroNumerosTest {

    // Given, When, Then
    // Dado, Quando, Então
    @Test
    public void Dado_uma_lista_de_numeros_quando_filtrar_numeros_pares_entao_retorna_lista_de_numeros_pares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosParesEsperados = Arrays.asList(2, 4);
        List<Integer> resultado = FiltroNumeros.numerosPares(numeros);
        assertIterableEquals(numerosParesEsperados, resultado, "O retorno da lista e sua ordem estão incorretos");
    }

    // Given, When, Then
    // Dado, Quando, Então
    @Test
    public void Dado_uma_lista_de_numeros_quando_filtrar_numeros_impares_entao_retorna_lista_de_numeros_impares() {
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4);
        List<Integer> numerosImparesEsperados = Arrays.asList(1, 3);
        List<Integer> resultado = FiltroNumeros.numerosImpares(numeros);
        assertIterableEquals(numerosImparesEsperados, resultado, "O retorno da lista e sua ordem estão incorretos");
    }

}