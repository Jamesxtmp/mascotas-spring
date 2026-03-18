package com.example.mascotas.direccion;

import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.clientes.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

    @Autowired
    private DireccionRepository direccionRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<Iterable<Direccion>> findAll() {
        return ResponseEntity.ok(direccionRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Direccion> findById(@PathVariable Long id) {
        Optional<Direccion> direccionOptional = direccionRepository.findById(id);
        return direccionOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Direccion> create(@RequestBody Direccion direccion) {
        if (direccion.getCliente() == null || direccion.getCliente().getIdCliente() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Cliente> clienteOptional = clienteRepository.findById(direccion.getCliente().getIdCliente());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        direccion.setCliente(clienteOptional.get());
        return ResponseEntity.ok(direccionRepository.save(direccion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direccion> update(@PathVariable Long id, @RequestBody Direccion direccionDetails) {
        Optional<Direccion> direccionOptional = direccionRepository.findById(id);
        if (direccionOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Direccion direccion = direccionOptional.get();
        direccion.setCalle(direccionDetails.getCalle());
        direccion.setNumero(direccionDetails.getNumero());
        return ResponseEntity.ok(direccionRepository.save(direccion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!direccionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        direccionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}