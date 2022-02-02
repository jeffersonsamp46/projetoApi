package io.github.jeffersonsamp46.rest.controller;

import io.github.jeffersonsamp46.domain.entity.Produto;
import io.github.jeffersonsamp46.domain.repository.Produtos;
import org.springframework.expression.spel.ast.OpMinus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "/api/produto")
@RestController
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos){
        this.produtos = produtos;
    }

    @PostMapping
    public ResponseEntity saveProduto(@RequestBody Produto produto){
        Produto produtoSalvo = produtos.save(produto);

        return ResponseEntity.ok(produtoSalvo);
    }

    @GetMapping("{codigo}")
    public ResponseEntity getProduto(@PathVariable("codigo") Integer id){
        Optional<Produto> produto = produtos.findById(id);

        if(produto.isPresent()){
            return  ResponseEntity.ok(produto);
        }

        return  ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity atualizaProduto(@PathVariable Integer id, @RequestBody Produto produto){

        return produtos.findById(id)
                .map(produtoExistente -> {
                    produto.setId(id);
                    produtos.save(produto);
                    return ResponseEntity.noContent().build();
                }  ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletarProduto(@PathVariable Integer id){
        Optional<Produto> produto = produtos.findById(id);
        if(produto.isPresent()){
            produtos.delete(produto.get());

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
