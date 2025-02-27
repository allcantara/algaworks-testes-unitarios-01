package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CarrinhoCompra {

    private final Cliente cliente;
    private final List<ItemCarrinhoCompra> itens;

    public CarrinhoCompra(Cliente cliente) {
        this(cliente, new ArrayList<>());
    }

    public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
        Objects.requireNonNull(cliente);
        Objects.requireNonNull(itens);
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
    }

    public List<ItemCarrinhoCompra> getItens() {
        return new ArrayList<>(this.itens);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        Objects.requireNonNull(produto);
        validarQuantidadeMinima(quantidade);

        Optional<ItemCarrinhoCompra> itemFiltrado = buscarItemPeloProduto(produto);

        itemFiltrado.ifPresentOrElse(item -> item.adicionarQuantidade(quantidade), () -> adicionarNovoItem(produto, quantidade));
    }

    public void removerProduto(Produto produto) {
        Objects.requireNonNull(produto);
        ItemCarrinhoCompra itemCarrinhoCompra = buscarItemPeloProduto(produto).orElseThrow(RuntimeException::new);
        this.itens.remove(itemCarrinhoCompra);
    }

    public void aumentarQuantidadeProduto(Produto produto) {
        Objects.requireNonNull(produto);

        ItemCarrinhoCompra itemCarrinhoCompra = buscarItemPeloProduto(produto).orElseThrow(RuntimeException::new);
        itemCarrinhoCompra.adicionarQuantidade(1);
    }

    public void diminuirQuantidadeProduto(Produto produto) {
        Objects.requireNonNull(produto);
        ItemCarrinhoCompra itemCarrinhoCompra = buscarItemPeloProduto(produto).orElseThrow(RuntimeException::new);

        if (itemCarrinhoCompra.getQuantidade() == 1) {
            this.itens.remove(itemCarrinhoCompra);
        } else {
            itemCarrinhoCompra.subtrairQuantidade(1);
        }

    }

    public BigDecimal getValorTotal() {
        return this.itens.stream()
                .map(ItemCarrinhoCompra::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getQuantidadeTotalDeProdutos() {
        return this.itens.stream()
                .map(ItemCarrinhoCompra::getQuantidade)
                .reduce(0, Integer::sum);
    }

    public void esvaziar() {
        this.itens.clear();
    }

    private void adicionarNovoItem(Produto produto, int quantidade) {
        this.itens.add(new ItemCarrinhoCompra(produto, quantidade));
    }

    private Optional<ItemCarrinhoCompra> buscarItemPeloProduto(Produto produto) {
        return this.itens
                .stream()
                .filter(item -> item.getProduto().equals(produto))
                .findFirst();
    }

    private static void validarQuantidadeMinima(int quantidade) {
        if (quantidade < 1) {
            throw new IllegalArgumentException("A quantidade não pode ser menor que 1");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarrinhoCompra that = (CarrinhoCompra) o;
        return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itens, cliente);
    }
}