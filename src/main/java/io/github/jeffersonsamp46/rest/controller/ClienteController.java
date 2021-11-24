package io.github.jeffersonsamp46.rest.controller;

import com.sun.org.apache.regexp.internal.RE;
import io.github.jeffersonsamp46.domain.entity.Cliente;
import io.github.jeffersonsamp46.domain.repository.Clientes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class ClienteController {

    Clientes clientes;

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

}
