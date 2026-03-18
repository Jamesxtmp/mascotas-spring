package com.example.mascotas.clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<Iterable<Cliente>> findAll() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        return clienteOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        if (cliente.getDireccion() != null) {
            cliente.getDireccion().setCliente(cliente);
        }
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = clienteOptional.get();
        cliente.setNombre(clienteDetails.getNombre());
        cliente.setApePaterno(clienteDetails.getApePaterno());
        cliente.setApeMaterno(clienteDetails.getApeMaterno());
        cliente.setEmail(clienteDetails.getEmail());

        if (clienteDetails.getDireccion() != null) {
            if (cliente.getDireccion() == null) {
                cliente.setDireccion(clienteDetails.getDireccion());
                cliente.getDireccion().setCliente(cliente);
            } else {
                cliente.getDireccion().setCalle(clienteDetails.getDireccion().getCalle());
                cliente.getDireccion().setNumero(clienteDetails.getDireccion().getNumero());
            }
        }
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}