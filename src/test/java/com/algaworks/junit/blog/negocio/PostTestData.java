package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;

import java.math.BigDecimal;

import static java.lang.Boolean.TRUE;

public class PostTestData {

    public static Post.Builder novoPost() {
        return Post.builder()
                .titulo("Olá mundo Java")
                .conteudo("Olá Java com System.out.println")
                .autor(EditorTestData.getEditorNovo().build())
                .pago(TRUE)
                .publicado(TRUE);
    }

    public static Post.Builder novoPostComId() {
        return novoPost()
                .id(1L)
                .slug("ola-mundo-java")
                .pago(TRUE)
                .ganhos(new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(10)));
    }

}
