package com.algaworks.junit.utilidade;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ContaBancariaTest {

    @Nested
    class SaldoInicial {
        @Test
        void deveLancarExceptionCasoSaldoSejaNull() {
            IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class,
                    () -> new ContaBancaria(null));
            assertEquals("O saldo nao pode ser null", illegalArgumentException.getMessage());
        }

        @Test
        void deveAdicionarSaldoInicialSemErros() {
            BigDecimal saldoInicial = BigDecimal.valueOf(100);
            ContaBancaria contaBancaria = new ContaBancaria(saldoInicial);
            assertEquals(saldoInicial, contaBancaria.saldo());
        }
    }

    @Nested
    class Saque {
        @Test
        void deveLancarExceptionCasoSaqueSejaMenorOuIgualAZero() {
            BigDecimal saldoInicial = BigDecimal.valueOf(100);
            ContaBancaria contaBancaria = new ContaBancaria(saldoInicial);
            assertAll("Validando saque na conta",
                    () -> assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.valueOf(-1))),
                    () -> assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.valueOf(0))),
                    () -> assertThrows(RuntimeException.class, () -> contaBancaria.saque(BigDecimal.valueOf(150))),
                    () -> assertDoesNotThrow(() -> contaBancaria.saque(BigDecimal.valueOf(1)))
            );
        }

        @Test
        void deveRealizarSaqueSemErros() {
            BigDecimal saldoInicial = BigDecimal.valueOf(100);
            BigDecimal valorSaque = BigDecimal.valueOf(20);
            BigDecimal saldoFinal = saldoInicial.subtract(valorSaque);

            ContaBancaria contaBancaria = new ContaBancaria(saldoInicial);
            assertDoesNotThrow(() -> contaBancaria.saque(valorSaque));
            assertEquals(saldoFinal, contaBancaria.saldo());
        }

        @Test
        void deveRealizarSaqueAposDepositoSemErros() {
            BigDecimal saldoInicial = BigDecimal.valueOf(100);
            BigDecimal valorDeposito = BigDecimal.valueOf(50);
            BigDecimal valorSaque = BigDecimal.valueOf(20);
            BigDecimal saldoFinal = saldoInicial.add(valorDeposito).subtract(valorSaque);

            ContaBancaria contaBancaria = new ContaBancaria(saldoInicial);
            assertDoesNotThrow(() -> contaBancaria.deposito(valorDeposito));
            assertDoesNotThrow(() -> contaBancaria.saque(valorSaque));
            assertEquals(saldoFinal, contaBancaria.saldo());
        }
    }

    @Nested
    class Deposito {
        @Test
        void deveLancarExceptionCasoDepositoSejaMenorOuIgualAZero() {
            BigDecimal saldoInicial = BigDecimal.valueOf(100);
            ContaBancaria contaBancaria = new ContaBancaria(saldoInicial);
            assertAll("Validando depósito na conta",
                    () -> assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(BigDecimal.valueOf(-1))),
                    () -> assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(BigDecimal.valueOf(0))),
                    () -> assertDoesNotThrow(() -> contaBancaria.deposito(BigDecimal.valueOf(1)))
            );
        }

        @Test
        void deveRealizarDepositoSemErros() {
            BigDecimal saldoInicial = BigDecimal.valueOf(100);
            BigDecimal valorDeposito = BigDecimal.valueOf(20);
            BigDecimal saldoFinal = saldoInicial.add(valorDeposito);

            ContaBancaria contaBancaria = new ContaBancaria(saldoInicial);
            assertDoesNotThrow(() -> contaBancaria.deposito(valorDeposito));
            assertEquals(saldoFinal, contaBancaria.saldo());
        }


        @Test
        void deveRealizarDepositoAposSaqueSemErros() {
            BigDecimal saldoInicial = BigDecimal.valueOf(100);
            BigDecimal valorDeposito = BigDecimal.valueOf(50);
            BigDecimal valorSaque = BigDecimal.valueOf(20);
            BigDecimal saldoFinal = saldoInicial.subtract(valorSaque).add(valorDeposito);

            ContaBancaria contaBancaria = new ContaBancaria(saldoInicial);
            assertDoesNotThrow(() -> contaBancaria.saque(valorSaque));
            assertDoesNotThrow(() -> contaBancaria.deposito(valorDeposito));
            assertEquals(saldoFinal, contaBancaria.saldo());
        }
    }

}
