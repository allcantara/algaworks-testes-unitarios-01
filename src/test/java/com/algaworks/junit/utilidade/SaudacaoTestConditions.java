package com.algaworks.junit.utilidade;

import org.assertj.core.api.Condition;

public class SaudacaoTestConditions {

    private SaudacaoTestConditions() {}

    public static Condition<String> igualBomDia() {
        return igual("Bom dia");
    }

    public static Condition<String> igual(String saudacaoCorreta) {
        return new Condition<>(e -> e.equals(saudacaoCorreta), "igual a %s", saudacaoCorreta);
    }

}
