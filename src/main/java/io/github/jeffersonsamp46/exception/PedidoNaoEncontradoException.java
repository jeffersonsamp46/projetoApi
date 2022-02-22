package io.github.jeffersonsamp46.exception;

public class PedidoNaoEncontradoException extends RuntimeException{
    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado.");
    }
}
