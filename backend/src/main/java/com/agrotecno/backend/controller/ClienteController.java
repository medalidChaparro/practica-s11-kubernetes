package com.agrotecno.backend.controller;

import com.agrotecno.backend.model.Cliente;
import com.agrotecno.backend.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Endpoint principal requerido: GET /api/clientes
     * Devuelve el listado completo de clientes reactivamente desde MongoDB.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    /**
     * Endpoint auxiliar: GET /api/clientes/{id}
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Cliente>> obtenerClientePorId(@PathVariable String id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint auxiliar: POST /api/clientes
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Cliente> crearCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}

