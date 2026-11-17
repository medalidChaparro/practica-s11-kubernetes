package com.agrotecno.backend.config;

import com.agrotecno.backend.model.Cliente;
import com.agrotecno.backend.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Inicializador de datos de prueba para asegurar que el endpoint /api/clientes
 * responda con los datos de verificación esperados en la práctica de Kubernetes.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    private final ClienteRepository clienteRepository;

    public DataInitializer(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void run(String... args) {
        clienteRepository.count()
                .flatMap(count -> {
                    if (count == 0) {
                        log.info("Base de datos vacía. Insertando cliente de prueba inicial...");
                        Cliente clienteInicial = new Cliente(
                                "6afb9f30e9e6da71136cca90",
                                "Cliente Prueba Kubernetes",
                                "999888777"
                        );
                        return clienteRepository.save(clienteInicial);
                    } else {
                        log.info("La colección clientes ya cuenta con {} registros.", count);
                        return clienteRepository.findAll().next();
                    }
                })
                .doOnSuccess(cliente -> log.info("Inicialización de datos completada: {}", cliente))
                .doOnError(error -> log.warn("Nota: No se pudo conectar a MongoDB de inmediato para inicializar datos: {}", error.getMessage()))
                .subscribe();
    }
}

