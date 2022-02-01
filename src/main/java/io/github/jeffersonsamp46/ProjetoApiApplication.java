package io.github.jeffersonsamp46;

import io.github.jeffersonsamp46.domain.entity.Cliente;
import io.github.jeffersonsamp46.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ProjetoApiApplication {

    /*
    @Bean
    public CommandLineRunner commandLineRunner(@Autowired Clientes clientes){
        return args -> {
            Cliente j = new Cliente("Jefferson Sampaio");
            Cliente a = new Cliente("Apolo Sampaio");
            Cliente p = new Cliente("Priscila Sampaio");
            clientes.save(j);
            clientes.save(a);
            clientes.save(p);
        };
    }
     */

    public static void main(String[] args) {
        SpringApplication.run(ProjetoApiApplication.class, args);
    }
}
