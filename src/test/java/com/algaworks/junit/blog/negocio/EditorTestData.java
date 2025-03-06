package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.modelo.Editor;

import java.math.BigDecimal;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class EditorTestData {

    public static Editor.Builder getEditorNovo() {
        return Editor.builder()
                .nome("Bruno")
                .email("bruno@email")
                .valorPagoPorPalavra(BigDecimal.TEN)
                .premium(TRUE);
    }

    public static Editor.Builder getEditorExistente() {
        return getEditorNovo().id(1L);
    }

    public static Editor.Builder getEditorInexistente() {
        return getEditorNovo().id(99L);
    }
}
