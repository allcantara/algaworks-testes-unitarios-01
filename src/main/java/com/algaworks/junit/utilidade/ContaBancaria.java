package com.algaworks.junit.utilidade;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

public class ContaBancaria {

    private BigDecimal saldoConta;

    public ContaBancaria(BigDecimal saldo) {
        //TODO 1 - validar saldo: não pode ser nulo, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - pode ser zero ou negativo

        if (isNull(saldo)) {
            throw new IllegalArgumentException("O saldo nao pode ser null");
        }

        this.saldoConta = saldo;
    }

    public void saque(BigDecimal valor) {
        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - Deve subtrair o valor do saldo
        //TODO 3 - Se o saldo for insuficiente deve lançar uma RuntimeException

        validarValorSolicitado(valor);

        if (this.saldoConta.compareTo(valor) < 0) {
            throw new RuntimeException("Saldo insuficiente");
        }

        this.saldoConta = this.saldoConta.subtract(valor);
    }

    public void deposito(BigDecimal valor) {
        //TODO 1 - validar valor: não pode ser nulo, zero ou menor que zero, caso seja, deve lançar uma IllegalArgumentException
        //TODO 2 - Deve adicionar o valor ao saldo

        validarValorSolicitado(valor);
        this.saldoConta = this.saldoConta.add(valor);
    }

    private static void validarValorSolicitado(BigDecimal valor) {
        if (isNull(valor) || valor.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("O saldo não pode ser null ou menor/igual a zero");
        }
    }

    public BigDecimal saldo() {
        //TODO 1 - retornar saldo
        return this.saldoConta;
    }
}
