package io.github.jeffersonsamp46.domain.repository;

import io.github.jeffersonsamp46.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Clientes extends JpaRepository<Cliente, Integer> {

    @Query(value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente > encontrarPornome(@Param("nome") String nome);

    boolean existsByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos where c.id=:id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);



}
