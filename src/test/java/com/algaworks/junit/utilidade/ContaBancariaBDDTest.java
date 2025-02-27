package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Conta Bancaria BDD")
public class ContaBancariaBDDTest {

    @Nested
    @DisplayName("Dado uma conta bancária com saldo de R$ 10,00")
    class ContaBancariaComSaldoDezReais {

        private ContaBancaria conta;

        @BeforeEach
        void beforeEach() {
            conta = new ContaBancaria(BigDecimal.TEN);
        }

        @Nested
        @DisplayName("Quando efetuar saque com valor menor")
        class SaqueValorMenor {

            private final BigDecimal valorSaque = BigDecimal.valueOf(9);

            @Test
            @DisplayName("Não deve lançar exception para o saque")
            void naoDeveLancarException() {
                assertDoesNotThrow(() -> conta.saque(valorSaque));
            }

            @Test
            @DisplayName("Deve subtrair o valor do saldo")
            void deveSubtrairOValorDoSaldo() {
                conta.saque(valorSaque);
                assertEquals(BigDecimal.ONE, conta.saldo());
            }

        }

        @Nested
        @DisplayName("Quando efetuar saque com valor maior")
        class SaqueValorMaior {

            private final BigDecimal valorSaque = new BigDecimal("20.00");

            @Test
            @DisplayName("Então deve lançar exception para o saque")
            void lancarException() {
                assertThrows(RuntimeException.class, () -> conta.saque(valorSaque));
            }

            @Test
            @DisplayName("Não deve alterar o saldo")
            void naoDeveAlterarSaldo() {
                try {
                    conta.saque(valorSaque);
                } catch (RuntimeException e) {
                    // Nao faz nada
                }

                assertEquals(BigDecimal.TEN, conta.saldo());
            }

        }

    }

    @Nested
    @DisplayName("Dado uma conta bancária com saldo zerado")
    class ContaBancariaComSaldoZerado {

        private ContaBancaria conta;

        @BeforeEach
        void beforeEach() {
            conta = new ContaBancaria(BigDecimal.ZERO);
        }

        @Nested
        @DisplayName("Quando efetuar saque com valor maior")
        class SaqueContaZerada {

            private final BigDecimal valorSaque = BigDecimal.ONE;

            @Test
            @DisplayName("Então deve lançar exception para o saque")
            void lancarException() {
                assertThrows(RuntimeException.class, () -> conta.saque(valorSaque));
            }

        }

        @Nested
        @DisplayName("Quando efetuar um depósito")
        class DepositoContaZerada {

            private final BigDecimal valorDeposito = new BigDecimal("20.00");

            @Test
            @DisplayName("Então deve somar ao saldo")
            void lancarException() {
                conta.deposito(valorDeposito);
                assertEquals(valorDeposito, conta.saldo());
            }

        }

    }

}
