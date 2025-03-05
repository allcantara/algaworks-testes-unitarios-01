package com.algaworks.junit.blog.negocio;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CadastroEditorComMockTest {

    @Captor
    ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

    @InjectMocks
    CadastroEditor cadastroEditor;

    @Mock
    ArmazenamentoEditor armazenamentoEditor;

    @Mock
    GerenciadorEnvioEmail gerenciadorEnvioEmail;

    @Nested
    class CadastroEditorValido {
        @Spy
        Editor editor = new Editor(null, "Bruno", "bruno@email", BigDecimal.TEN, true);

        @BeforeEach
        void beforeEach() {
            when(armazenamentoEditor.salvar(any(Editor.class))).thenAnswer(invocacao -> {
                Editor editorPassado = invocacao.getArgument(0, Editor.class);
                editorPassado.setId(1L);
                return editorPassado;
            });
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro() {
            Editor editorSalvo = cadastroEditor.criar(editor);
            assertEquals(1L, editorSalvo.getId());
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento() {
            cadastroEditor.criar(editor);
            verify(armazenamentoEditor, times(1)).salvar(eq(editor));
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_e_lancar_exception_Entao_nao_deve_enviar_email() {
            when(armazenamentoEditor.salvar(editor)).thenThrow(RuntimeException.class);
            assertAll("Não deve enviar email quando lançar exception no armazenamento",
                    () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
                    () -> verify(gerenciadorEnvioEmail, never()).enviarEmail(any())
            );
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_para_o_editor() {
            // ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);

            Editor editorSalvo = cadastroEditor.criar(editor);

            verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());
            Mensagem mensagem = mensagemArgumentCaptor.getValue();
            assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
        }

        @Test
        void Dado_um_editor_valido_Quanto_cadastrar_Entao_deve_verificar_o_email() {
            cadastroEditor.criar(editor);
            verify(editor, atLeast(1)).getEmail();
        }

        @Test
        void Dado_um_editor_valido_Quando_criar_editor_com_email_existente_Entao_deve_lancar_exception() {
            when(armazenamentoEditor.encontrarPorEmail("bruno@email"))
                    .thenReturn(Optional.empty())
                    .thenReturn(Optional.of(editor));

            assertAll(
                    () -> assertDoesNotThrow(() -> cadastroEditor.criar(editor)),
                    () -> assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editor))
            );
        }

        @Test
        void Dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar() {
            cadastroEditor.criar(editor);

            InOrder ordemDasChamadas = inOrder(armazenamentoEditor, gerenciadorEnvioEmail);
            ordemDasChamadas.verify(armazenamentoEditor, times(1)).salvar(editor);
            ordemDasChamadas.verify(gerenciadorEnvioEmail, times(1)).enviarEmail(any());
        }
    }

    @Nested
    class CadastroEditorNull {

        @Test
        void Dado_um_editor_null_Quando_cadastrar_Entao_deve_lancar_exception() {
            assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
            verify(armazenamentoEditor, never()).salvar(any());
            verify(gerenciadorEnvioEmail, never()).enviarEmail(any());
        }

    }

    @Nested
    class EdicaoComEditorValido {

        @Spy
        Editor editor = new Editor(1L, "Bruno", "bruno@email", BigDecimal.TEN, true);

        @BeforeEach
        void beforeEach() {
            when(armazenamentoEditor.salvar(editor)).thenAnswer(invocacao -> invocacao.getArgument(0, Editor.class));
            when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));
        }

        @Test
        void Dado_um_editor_valido_Quando_editar_Entao_deve_alterar_editor_valido() {
            Editor editorAlterado = new Editor(1L, "Vinicius", "vinicius@email", BigDecimal.ZERO, false);
            cadastroEditor.editar(editorAlterado);
            verify(editor).atualizarComDados(editorAlterado);
            InOrder inOrder = inOrder(editor, armazenamentoEditor);
            inOrder.verify(editor).atualizarComDados(editorAlterado);
            inOrder.verify(armazenamentoEditor).salvar(editor);
        }

    }

    @Nested
    class EdicaoComEditorInexistente {

        Editor editor = new Editor(99L, "Bruno", "bruno@email", BigDecimal.TEN, true);

        @BeforeEach
        void beforeEach() {
            when(armazenamentoEditor.encontrarPorId(99L)).thenReturn(Optional.empty());
        }

        @Test
        void Dado_um_editor_inexistente_Quando_editar_Entao_deve_lancar_exception() {
            assertThrows(EditorNaoEncontradoException.class, () -> cadastroEditor.editar(editor));
            verify(armazenamentoEditor, never()).salvar(any());
        }

    }

}