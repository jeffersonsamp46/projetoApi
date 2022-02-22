package io.github.jeffersonsamp46.rest.controller;

import io.github.jeffersonsamp46.domain.entity.ItemPedido;
import io.github.jeffersonsamp46.domain.entity.Pedido;
import io.github.jeffersonsamp46.domain.enums.StatusPedido;
import io.github.jeffersonsamp46.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.jeffersonsamp46.rest.dto.InformacoesItemPedidoDTO;
import io.github.jeffersonsamp46.rest.dto.InformacoesPedidoDTO;
import io.github.jeffersonsamp46.rest.dto.PedidoDTO;
import io.github.jeffersonsamp46.service.PedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

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

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return  pedidoService
                .obterPedidoCompleto(id)
                .map( p -> converter(p))
                .orElseThrow( () ->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converter(Pedido pedido) {
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .status(pedido.getStatus().name())
                .items(converterItem(pedido.getItens()))
                .build();
    }

    private List<InformacoesItemPedidoDTO> converterItem(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }

        return itens.stream().map(
                item -> InformacoesItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }
}
