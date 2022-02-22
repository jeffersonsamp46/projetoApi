package io.github.jeffersonsamp46.rest.controller;

import io.github.jeffersonsamp46.domain.entity.Cliente;
import io.github.jeffersonsamp46.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController // nao preciso colocar @ResponseBody em cada metodo
@RequestMapping("/api/clientes/") // nao precisa passar essa url base em todos os metodos
public class
ClienteController {

    private  Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping(value = "{codigo}")
//    @ResponseBody
    public ResponseEntity getClienteById (@PathVariable("codigo") Integer id){
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
//    @ResponseBody
    public ResponseEntity saveCliente(@RequestBody Cliente cliente){
        Cliente clienteSalvo =  clientes.save(cliente);

        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("{codigo}")
//    @ResponseBody
    public ResponseEntity delete(@PathVariable("codigo")  Integer id){
        Optional<Cliente> cliente = clientes.findById(id);

        if(cliente.isPresent()){
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{codigo}")
//    @ResponseBody
    public ResponseEntity atualizaCliente( @PathVariable("codigo") Integer id,
                                           @RequestBody Cliente cliente){

        // Método do Professor
        return  clientes.findById(id)
                .map(clienteExistente ->{
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());

        /* Minha implementacao perguntei pro professor se é gambi
        Optional<Cliente> clienteAntigo = clientes.findById(id);
        if (clienteAntigo.isPresent()){
            cliente.setId(id);
            clientes.save(cliente);

            return ResponseEntity.ok(getClienteById(id).getBody());
        }
        else {
            return ResponseEntity.notFound().build();
        }
         */
    }


    @GetMapping
//    @ResponseBody
    public ResponseEntity find(Cliente filtro){
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, exampleMatcher);
        List<Cliente> lista = clientes.findAll(example);

        return ResponseEntity.ok(lista);
    }


}
