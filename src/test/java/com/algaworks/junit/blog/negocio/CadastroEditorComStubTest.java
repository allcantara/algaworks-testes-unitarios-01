package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorComStubTest {

    CadastroEditor cadastroEditor;
    ArmazenamentoEditorFixoEmMemoria armazenamentoEditor;
    Editor editor;

    @BeforeEach
    void beforeEach() {
        editor = new Editor(null, "Bruno", "bruno@email", BigDecimal.TEN, true);

        armazenamentoEditor = new ArmazenamentoEditorFixoEmMemoria();

        GerenciadorEnvioEmail gerenciadorEnvioEmail = new GerenciadorEnvioEmail() {
            @Override
            void enviarEmail(Mensagem mensagem) {
                System.out.println("Enviando mensagem para: " + mensagem.getDestinatario());
            }
        };

        cadastroEditor = new CadastroEditor(armazenamentoEditor, gerenciadorEnvioEmail);
    }

    @Test
    void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
        Editor editorSalvo = cadastroEditor.criar(editor);
        assertAll("Validando criação de editor novo",
                () -> assertEquals(1L, editorSalvo.getId()),
                () -> assertTrue(armazenamentoEditor.chamouSalvar)
        );
    }

    @Test
    void Dado_um_editor_null_Quando_criar_Entao_deve_lancar_exception() {
        assertAll("Validando criação de editor null",
                () -> assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null)),
                () -> assertFalse(armazenamentoEditor.chamouSalvar)
        );
    }

    @Test
    void Dado_um_editor_com_email_existente_Quando_criar_Entao_deve_lancar_exception() {
        editor.setEmail("bruno.existe@email.com");
        assertAll("Validando criação de editor com e-mail existente",
                () -> assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editor)),
                () -> assertFalse(armazenamentoEditor.chamouSalvar)
        );
    }

}