package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaTest {

    @Test
    void assercaoAgrupada() {
        Pessoa pessoa = new Pessoa("Bruno", "Alcântara");
        assertAll("Asserções de pessoa",
                () -> assertEquals("Bruno", pessoa.getNome()),
                () -> assertEquals("Alcântara", pessoa.getSobrenome())
                );
    }

}