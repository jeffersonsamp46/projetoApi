package io.github.jeffersonsamp46.domain.repository;


import io.github.jeffersonsamp46.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

}
