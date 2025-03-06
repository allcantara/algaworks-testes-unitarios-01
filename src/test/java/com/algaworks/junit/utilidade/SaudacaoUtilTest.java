package com.algaworks.junit.utilidade;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.algaworks.junit.utilidade.SaudacaoTestConditions.igualBomDia;
import static com.algaworks.junit.utilidade.SaudacaoUtil.saudar;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do utilitário [SaudacaoUtil]")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SaudacaoUtilTest {

    @Test
    @DisplayName("Deve saudar com bom dia")
    public void saudarComBomDia() {
        int horaValida = 9;
        String saudacao = assertDoesNotThrow(() -> saudar(horaValida));
        assertEquals("Bom dia", saudacao);

        assertThat(saudacao).is(igualBomDia());
    }

    @Test
    @DisplayName("Deve saudar com bom dia a partir das 5h")
    public void saudarComBomDiaAPartirDas5h() {
        int horaValida = 5;
        String saudacao = assertDoesNotThrow(() -> saudar(horaValida));
        assertEquals("Bom dia", saudacao);
    }

    @Test
    @DisplayName("Deve saudar com boa tarde")
    public void saudarComBoaTarde() {
        int horaValida = 14;
        String saudacao = assertDoesNotThrow(() -> saudar(horaValida));
        assertEquals("Boa tarde", saudacao);
    }

    @Test
    @DisplayName("Deve saudar com boa noite")
    public void saudarComBoaNoite() {
        int horaValida = 20;
        String saudacao = assertDoesNotThrow(() -> saudar(horaValida));
        assertEquals("Boa noite", saudacao);
    }

    @Test
    public void Dado_um_horario_noturno_quando_saudar_entao_deve_retornar_boa_noite() {
        int horaValida = 4;
        String saudacao = assertDoesNotThrow(() -> saudar(horaValida));
        assertEquals("Boa noite", saudacao);
    }

    @Test
//    @Disabled("Teste [deveLancarException] desabilitado")
    public void Dado_uma_hora_invalida_quando_saudar_entao_deve_lanca_exception() {
        int horaInvalida = -2;

        assertThatThrownBy(() -> saudar(horaInvalida))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hora invalida");
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
    public void Dado_um_horario_matinal_Quando_saudar_entao_deve_retornar_bom_dia(int horaValida) {
        String saudacao = assertDoesNotThrow(() -> saudar(horaValida));
        assertEquals("Bom dia", saudacao);
    }

}