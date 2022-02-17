package io.github.jeffersonsamp46.rest.controller;

import io.github.jeffersonsamp46.domain.entity.Pedido;
import io.github.jeffersonsamp46.rest.dto.PedidoDTO;
import io.github.jeffersonsamp46.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService service) {
        this.pedidoService = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save (@RequestBody PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);

        return pedido.getId();
    }
}
