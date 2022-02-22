package io.github.jeffersonsamp46.service;

import io.github.jeffersonsamp46.domain.entity.Pedido;
import io.github.jeffersonsamp46.domain.enums.StatusPedido;
import io.github.jeffersonsamp46.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);}
