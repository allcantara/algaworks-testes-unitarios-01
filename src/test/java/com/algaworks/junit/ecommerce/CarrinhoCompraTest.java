package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Carrinho de Compras")
class CarrinhoCompraTest {

    @Nested
    @DisplayName("Dado carrinho de compra sem itens")
    class CarrinhoDeComprasSemItens {

        private CarrinhoCompra carrinho;
        private static Cliente cliente;
        private static Produto produto;

        @BeforeAll
        static void beforeAll() {
            cliente = new Cliente(1L, "Bruno");
            produto = new Produto(1L, "Notebook", "Notebook Dell", new BigDecimal("3000"));
        }

        @Nested
        @DisplayName("Quando criar carrinho")
        class CriarCarrinhoSemItens {

            @Test
            @DisplayName("Deve criar carrinho sem erros")
            void criaCarrinhoSemErros() {
                assertDoesNotThrow(() -> new CarrinhoCompra(cliente));
            }

            @Test
            @DisplayName("Deve lançar exception quando cliente for null")
            void criaCarrinhoComErro() {
                assertThrows(NullPointerException.class, () -> new CarrinhoCompra(null));
            }

            @Test
            @DisplayName("Deve existir cliente cadastrado")
            void consultaClienteCadastrado() {
                carrinho = new CarrinhoCompra(cliente);
                assertEquals(cliente, carrinho.getCliente());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0")
            void consultaQuantidadeDeItensIniciais() {
                carrinho = new CarrinhoCompra(cliente);
                assertEquals(0, carrinho.getItens().size());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 0")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho = new CarrinhoCompra(cliente);
                assertEquals(0, carrinho.getQuantidadeTotalDeProdutos());
            }

        }

        @Nested
        @DisplayName("Quando adicionar produto ao carrinho")
        class AdicionarProdutoCarrinhoSemItens {

            private static CarrinhoCompra carrinho;

            @BeforeEach
            void beforeEach() {
                carrinho = new CarrinhoCompra(cliente);
            }

            @Test
            @DisplayName("Deve lançar exception para produto null")
            void adicionaProdutoAoCarrinhoComErros() {
                assertThrows(NullPointerException.class, () -> carrinho.adicionarProduto(null, 1));
            }

            @Test
            @DisplayName("Deve lançar exception para quantidade inválida")
            void adicionaProdutoAoCarrinhoComQuantidadeInvalida() {
                assertThrows(IllegalArgumentException.class, () -> carrinho.adicionarProduto(produto, 0));
            }

            @Test
            @DisplayName("Não deve lançar exception ao adicionar produto novo")
            void adicionaProdutoAoCarrinhoSemErros() {
                assertDoesNotThrow(() -> carrinho.adicionarProduto(produto, 1));
            }

            @Test
            @DisplayName("A itens de produtos deve ser 1")
            void consultaQuantidadeDeItens() {
                carrinho.adicionarProduto(produto, 1);
                assertEquals(1, carrinho.getItens().size());
            }

            @Test
            @DisplayName("O valor total dos produtos deve ser 3000")
            void consultaValorTotalProdutos() {
                carrinho.adicionarProduto(produto, 1);
                assertEquals(new BigDecimal("3000"), carrinho.getValorTotal());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 1")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho.adicionarProduto(produto, 1);
                assertEquals(1, carrinho.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após esvaziar carrinho")
            void esvaziarCarrinho() {
                carrinho.esvaziar();
                assertEquals(0, carrinho.getItens().size());
            }

        }

        @Nested
        @DisplayName("Quando remover produto do carrinho")
        class RemoverProdutoCarrinhoSemItens {

            private static CarrinhoCompra carrinho;

            @BeforeEach
            void beforeEach() {
                carrinho = new CarrinhoCompra(cliente);
                carrinho.adicionarProduto(produto, 1);
            }

            @Test
            @DisplayName("Deve lançar exeption para produto null")
            void removeProdutoVazio() {
                assertThrows(NullPointerException.class, () -> carrinho.removerProduto(null));
            }

            @Test
            @DisplayName("Deve lançar exeption para produto inexistente")
            void removeProdutoInexistente() {
                var produto = new Produto(2L, "Mouse", "Mouse Gamer", new BigDecimal("500"));
                assertThrows(RuntimeException.class, () -> carrinho.removerProduto(produto));
            }

            @Test
            @DisplayName("Deve remover produto existente sem erros")
            void removeProdutoExistente() {
                assertDoesNotThrow(() -> carrinho.removerProduto(produto));
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após remover")
            void consultarQuantidadeProdutoAposRemover() {
                carrinho.removerProduto(produto);
                assertEquals(0, carrinho.getItens().size());
            }

            @Test
            @DisplayName("O valor total deve ser 0 após remover")
            void consultarValorTotalAposRemoverProduto() {
                carrinho.removerProduto(produto);
                assertEquals(BigDecimal.ZERO, carrinho.getValorTotal());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 0")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho.removerProduto(produto);
                assertEquals(0, carrinho.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após esvaziar carrinho")
            void esvaziarCarrinho() {
                carrinho.esvaziar();
                assertEquals(0, carrinho.getItens().size());
            }

        }

    }

    @Nested
    @DisplayName("Dado carrinho de compra com itens iniciais")
    class CarrinhoDeComprasComItens {

        private CarrinhoCompra carrinho;
        private static Cliente cliente;
        private static Produto produto;
        private static ItemCarrinhoCompra itemCarrinho;

        @BeforeEach
        void beforeEach() {
            cliente = new Cliente(1L, "Bruno");
            produto = new Produto(1L, "Notebook", "Notebook Dell", new BigDecimal("3000"));
            itemCarrinho = new ItemCarrinhoCompra(produto, 1);
        }

        @Nested
        @DisplayName("Quando criar carrinho com itens iniciais")
        class CriarCarrinhoComItens {

            @Test
            @DisplayName("Deve criar carrinho com produto e itens sem erros")
            void criaCarrinhoSemErros() {
                assertDoesNotThrow(() -> new CarrinhoCompra(cliente, List.of(itemCarrinho)));
            }

            @Test
            @DisplayName("Deve lançar exception para carrinho null")
            void criaCarrinhoComErro() {
                assertThrows(NullPointerException.class, () -> new CarrinhoCompra(cliente, null));
            }

            @Test
            @DisplayName("Deve existir cliente cadastrado")
            void consultaClienteCadastrado() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
                assertEquals(cliente, carrinho.getCliente());
            }

            @Test
            @DisplayName("A quantidade de itens iniciais deve ser 1")
            void consultaQuantidadeDeItensIniciais() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
                assertEquals(1, carrinho.getItens().size());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 1")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
                assertEquals(1, carrinho.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após esvaziar carrinho")
            void esvaziarCarrinho() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
                carrinho.esvaziar();
                assertEquals(0, carrinho.getItens().size());
            }

        }

        @Nested
        @DisplayName("Quando adicionar outro produto ao carrinho")
        class AdicionarProdutoCarrinhoComItens {

            private static CarrinhoCompra carrinho;

            @BeforeEach
            void beforeEach() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
            }

            @Test
            @DisplayName("Não deve lançar exception ao adicionar produto novo")
            void adicionaProdutoAoCarrinhoSemErros() {
                Produto produtoNovo = new Produto(2L, "Teclado", "Teclado Gamer", new BigDecimal("700"));
                assertDoesNotThrow(() -> carrinho.adicionarProduto(produtoNovo, 1));
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 2")
            void consultaQuantidadeDeItens() {
                Produto produtoNovo = new Produto(2L, "Teclado", "Teclado Gamer", new BigDecimal("700"));
                carrinho.adicionarProduto(produtoNovo, 1);
                assertEquals(2, carrinho.getItens().size());
            }

            @Test
            @DisplayName("O valor total dos produtos deve ser 3700")
            void consultaValorTotalProdutos() {
                Produto produtoNovo = new Produto(2L, "Teclado", "Teclado Gamer", new BigDecimal("700"));
                carrinho.adicionarProduto(produtoNovo, 1);
                assertEquals(new BigDecimal("3700"), carrinho.getValorTotal());
            }

            @Test
            @DisplayName("Não deve lançar exception ao adicionar produto já existente")
            void adicionaProdutoExistenteAoCarrinhoSemErros() {
                assertDoesNotThrow(() -> carrinho.adicionarProduto(produto, 1));
            }

            @Test
            @DisplayName("A quantidade de itens já existentes deve continuar sendo 1")
            void consultaQuantidadeDeItensParaProdutoExistente() {
                carrinho.adicionarProduto(produto, 2);
                assertEquals(1, carrinho.getItens().size());
            }

            @Test
            @DisplayName("O valor total dos produtos já existentes deve ser 6000")
            void consultaValorTotalParaProdutoExistente() {
                carrinho.adicionarProduto(produto, 1);
                assertEquals(new BigDecimal("6000"), carrinho.getValorTotal());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 2 após adicionar novo produto")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho.adicionarProduto(produto, 1);
                assertEquals(2, carrinho.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após esvaziar carrinho")
            void esvaziarCarrinho() {
                carrinho.adicionarProduto(produto, 1);
                carrinho.esvaziar();
                assertEquals(0, carrinho.getItens().size());
            }

            @Test
            @DisplayName("Deve conter apenas produtos adicionados")
            void deveConterProdutosAdicionados() {
                carrinho.adicionarProduto(produto, 1);
                Produto novoProduto = new Produto(2L, "Maquina de cafe", "Maquina de fazer café", new BigDecimal("1000"));
                carrinho.adicionarProduto(novoProduto, 1);

                Produto produtoNaoAdicionado = new Produto(2L, "Monitor", "Monitor Dell", new BigDecimal("1300"));

                assertThat(carrinho.getItens())
                        .flatMap(ItemCarrinhoCompra::getProduto)
                        .contains(produto, novoProduto)
                        .doesNotContain(produtoNaoAdicionado);
            }

        }

        @Nested
        @DisplayName("Quando remover produto do carrinho com 2 itens")
        class RemoverProdutoCarrinhoComItens {

            private static CarrinhoCompra carrinho;
            private Produto produtoNovo;

            @BeforeEach
            void beforeEach() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
                produtoNovo = new Produto(2L, "Teclado", "Teclado Gamer", new BigDecimal("1000"));
                carrinho.adicionarProduto(produtoNovo, 1);
            }

            @Test
            @DisplayName("Deve diminuir quantidade de produto existente sem erros")
            void removeProdutoExistente() {
                assertDoesNotThrow(() -> carrinho.removerProduto(produtoNovo));
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 1 após remover um produto")
            void consultarQuantidadeProdutoAposRemover() {
                carrinho.removerProduto(produtoNovo);
                assertEquals(1, carrinho.getItens().size());
            }

            @Test
            @DisplayName("O valor total deve ser 3000 após remover")
            void consultarValorTotalAposRemoverProduto() {
                carrinho.removerProduto(produtoNovo);
                assertEquals(new BigDecimal("3000"), carrinho.getValorTotal());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 1 após remover novo produto")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho.removerProduto(produtoNovo);
                assertEquals(1, carrinho.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após esvaziar carrinho")
            void esvaziarCarrinho() {
                carrinho.esvaziar();
                assertEquals(0, carrinho.getItens().size());
            }

        }

        @Nested
        @DisplayName("Quando aumentar a quantidade de produtos do carrinho com itens")
        class AumentarQuantidadeProdutoCarrinhoComItens {

            private static CarrinhoCompra carrinho;
            private Produto produtoNovo;

            @BeforeEach
            void beforeEach() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
                produtoNovo = new Produto(2L, "Teclado", "Teclado Gamer", new BigDecimal("1000"));
                carrinho.adicionarProduto(produtoNovo, 1);
            }

            @Test
            @DisplayName("Deve lançar exception para produto null")
            void diminuirQuantidadeProdutoNull() {
                assertThrows(NullPointerException.class, () -> carrinho.aumentarQuantidadeProduto(null));
            }

            @Test
            @DisplayName("Deve lançar exception para produto inexistente")
            void removeProdutoInexistente() {
                var produtoInexistente = new Produto(3L, "Mouse", "Mouse Gamer", new BigDecimal("500"));
                assertThrows(RuntimeException.class, () -> carrinho.aumentarQuantidadeProduto(produtoInexistente));
            }

            @Test
            @DisplayName("Deve aumentar a quantidade de um produto existente sem erros")
            void removeProdutoExistente() {
                assertDoesNotThrow(() -> carrinho.aumentarQuantidadeProduto(produtoNovo));
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 2 mesmo após aumentar quantidade de um produto")
            void consultarQuantidadeProdutoAposRemover() {
                carrinho.aumentarQuantidadeProduto(produtoNovo);
                assertEquals(2, carrinho.getItens().size());
            }

            @Test
            @DisplayName("O valor total deve ser 5k após aumentar a quantidade de um produto de 1k")
            void consultarValorTotalAposRemoverProduto() {
                carrinho.aumentarQuantidadeProduto(produtoNovo);
                assertEquals(new BigDecimal("5000"), carrinho.getValorTotal());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 3 após aumentar a quantidade do novo produto")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho.aumentarQuantidadeProduto(produtoNovo);
                assertEquals(3, carrinho.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após esvaziar carrinho")
            void esvaziarCarrinho() {
                carrinho.esvaziar();
                assertEquals(0, carrinho.getItens().size());
            }

        }

        @Nested
        @DisplayName("Quando diminuir a quantidade de produtos do carrinho com itens")
        class DiminuirQuantidadeProdutoCarrinhoComItens {

            private static CarrinhoCompra carrinho;
            private Produto produtoNovo;

            @BeforeEach
            void beforeEach() {
                carrinho = new CarrinhoCompra(cliente, List.of(itemCarrinho));
                produtoNovo = new Produto(2L, "Teclado", "Teclado Gamer", new BigDecimal("1000"));
                carrinho.adicionarProduto(produtoNovo, 2);
            }

            @Test
            @DisplayName("Deve lançar exception para produto null")
            void diminuirQuantidadeProdutoNull() {
                assertThrows(NullPointerException.class, () -> carrinho.diminuirQuantidadeProduto(null));
            }

            @Test
            @DisplayName("Deve lançar exception para produto inexistente")
            void removeProdutoInexistente() {
                var produtoInexistente = new Produto(3L, "Mouse", "Mouse Gamer", new BigDecimal("500"));
                assertThrows(RuntimeException.class, () -> carrinho.diminuirQuantidadeProduto(produtoInexistente));
            }

            @Test
            @DisplayName("Deve diminuir a quantidade de um produto existente sem erros")
            void removeProdutoExistente() {
                assertDoesNotThrow(() -> carrinho.diminuirQuantidadeProduto(produtoNovo));
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 2 mesmo após diminuir quantidade de um produto")
            void consultarQuantidadeProdutoAposRemover() {
                carrinho.diminuirQuantidadeProduto(produtoNovo);
                assertEquals(2, carrinho.getItens().size());
            }

            @Test
            @DisplayName("O valor total deve ser 4k após diminuir a quantidade de um produto de 1k")
            void consultarValorTotalAposRemoverProduto() {
                carrinho.diminuirQuantidadeProduto(produtoNovo);
                assertEquals(new BigDecimal("4000"), carrinho.getValorTotal());
            }

            @Test
            @DisplayName("A quantidade de produtos deve ser 2 após diminuir a quantidade do novo produto")
            void consultaQuantidadeDeProdutosIniciais() {
                carrinho.diminuirQuantidadeProduto(produtoNovo);
                assertEquals(2, carrinho.getQuantidadeTotalDeProdutos());
            }

            @Test
            @DisplayName("A quantidade de itens deve ser 0 após esvaziar carrinho")
            void esvaziarCarrinho() {
                carrinho.esvaziar();
                assertEquals(0, carrinho.getItens().size());
            }

        }

    }

}