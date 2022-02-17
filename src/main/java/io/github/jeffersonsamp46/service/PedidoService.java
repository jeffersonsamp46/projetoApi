package io.github.jeffersonsamp46.service;

import io.github.jeffersonsamp46.domain.entity.Pedido;
import io.github.jeffersonsamp46.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

}
