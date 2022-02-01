package io.github.jeffersonsamp46.rest.controller;

import com.sun.org.apache.regexp.internal.RE;
import io.github.jeffersonsamp46.domain.entity.Cliente;
import io.github.jeffersonsamp46.domain.repository.Clientes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ClienteController {

    private  Clientes clientes;

    public ClienteController(Clientes clientes) {
        this.clientes = clientes;
    }

    @GetMapping(value = "/api/cliente/{codigo}")
    @ResponseBody
    public ResponseEntity getClienteById (@PathVariable("codigo") Integer id){
        Optional<Cliente> cliente = clientes.findById(id);

        if (cliente.isPresent()){
            return ResponseEntity.ok(cliente.get());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/cliente")
    @ResponseBody
    public ResponseEntity saveCliente(@RequestBody Cliente cliente){
        Cliente clienteSalvo =  clientes.save(cliente);

        return ResponseEntity.ok(clienteSalvo);
    }

    @DeleteMapping("/api/cliente/{codigo}")
    @ResponseBody
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

    @PutMapping("/api/cliente/{codigo}")
    @ResponseBody
    public ResponseEntity atualizaCliente( @PathVariable("codigo") Integer id,
                                           @RequestBody Cliente cliente){

        Optional<Cliente> clienteAntigo = clientes.findById(id);
        if (clienteAntigo.isPresent()){
            cliente.setId(id);
            clientes.save(cliente);

            return ResponseEntity.ok(getClienteById(id).getBody());
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }



}
