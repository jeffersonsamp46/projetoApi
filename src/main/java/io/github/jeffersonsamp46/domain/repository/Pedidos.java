package io.github.jeffersonsamp46.domain.repository;


import io.github.jeffersonsamp46.domain.entity.Cliente;
import io.github.jeffersonsamp46.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Pedidos extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);

}
