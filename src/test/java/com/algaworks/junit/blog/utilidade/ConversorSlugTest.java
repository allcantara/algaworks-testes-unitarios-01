package com.algaworks.junit.blog.utilidade;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class ConversorSlugTest {

    @Test
    void deveConverterJuntoComCodigo() {
        try (MockedStatic<GeradorCodigo> geradorCodigoMock = mockStatic(GeradorCodigo.class)) {
            geradorCodigoMock.when(GeradorCodigo::gerar).thenReturn("123");

            String slug = ConversorSlug.converterJuntoComCodigo("teste conversor");
            assertEquals("teste-conversor-123", slug);
        }
    }

}