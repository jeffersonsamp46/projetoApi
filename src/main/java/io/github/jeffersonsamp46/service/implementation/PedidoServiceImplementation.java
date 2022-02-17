package io.github.jeffersonsamp46.service.implementation;

import io.github.jeffersonsamp46.domain.entity.Cliente;
import io.github.jeffersonsamp46.domain.entity.ItemPedido;
import io.github.jeffersonsamp46.domain.entity.Pedido;
import io.github.jeffersonsamp46.domain.entity.Produto;
import io.github.jeffersonsamp46.domain.repository.Clientes;
import io.github.jeffersonsamp46.domain.repository.ItemsPedido;
import io.github.jeffersonsamp46.domain.repository.Pedidos;
import io.github.jeffersonsamp46.domain.repository.Produtos;
import io.github.jeffersonsamp46.exception.RegraNegocioException;
import io.github.jeffersonsamp46.rest.dto.ItemPedidoDTO;
import io.github.jeffersonsamp46.rest.dto.PedidoDTO;
import io.github.jeffersonsamp46.service.PedidoService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImplementation implements PedidoService {

    private final Pedidos pedidos;
    private final Clientes clientes;
    private final Produtos produtos;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientes
                .findById(idCliente).orElseThrow( () -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        pedidos.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);

        return pedido;
    }

    private  List<ItemPedido> converterItems(Pedido pedido,List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw  new RegraNegocioException("Não é possivel realizar um pedido sem Itens");
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtos
                            .findById(idProduto)
                            .orElseThrow(()-> new RegraNegocioException("Código de produto inválido: "+idProduto));
                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                        }).collect(Collectors.toList());
    }
}
