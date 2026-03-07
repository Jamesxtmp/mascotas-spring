package com.example.mascotas.direccion;

import com.example.mascotas.clientes.ClienteRepository;
import com.example.mascotas.clientes.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/direccion")
public class DireccionController {
    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todas las direcciones
    @GetMapping()
    public ResponseEntity<Iterable<Direccion>> findAll() {
        return ResponseEntity.ok(direccionRepository.findAll());
    }

    // Obtener dirección por ID
    @GetMapping("/{idDireccion}")
    public ResponseEntity<Direccion> findById(@PathVariable Long idDireccion) {
        Optional<Direccion> direccionOptional = direccionRepository.findById(idDireccion);
        if (direccionOptional.isPresent()) {
            return ResponseEntity.ok(direccionOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nueva dirección
    @PostMapping()
    public ResponseEntity<Direccion> create(
            @RequestBody Direccion direccion,
            UriComponentsBuilder uriBuilder) {

        // Validar que el cliente existe
        if (direccion.getCliente() == null || direccion.getCliente().getIdCliente() == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Cliente> clienteOptional = clienteRepository.findById(direccion.getCliente().getIdCliente());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        direccion.setCliente(clienteOptional.get());
        Direccion direccionCreada = direccionRepository.save(direccion);
        URI uri = uriBuilder.path("/direccion/{idDireccion}").buildAndExpand(
                direccionCreada.getIdDireccion()
        ).toUri();
        return ResponseEntity.created(uri).body(direccionCreada);
    }

    // Actualizar dirección
    @PutMapping("/{idDireccion}")
    public ResponseEntity<Direccion> update(
            @PathVariable Long idDireccion,
            @RequestBody Direccion direccionActualizada) {
        Optional<Direccion> direccionOptional = direccionRepository.findById(idDireccion);
        if (!direccionOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Direccion direccionExistente = direccionOptional.get();
        direccionExistente.setCalle(direccionActualizada.getCalle());
        direccionExistente.setNumero(direccionActualizada.getNumero());

        // Validar y actualizar cliente si se proporciona
        if (direccionActualizada.getCliente() != null && direccionActualizada.getCliente().getIdCliente() != null) {
            Optional<Cliente> clienteOptional = clienteRepository.findById(direccionActualizada.getCliente().getIdCliente());
            if (clienteOptional.isPresent()) {
                direccionExistente.setCliente(clienteOptional.get());
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }

        Direccion direccionUpdate = direccionRepository.save(direccionExistente);
        return ResponseEntity.ok(direccionUpdate);
    }

    // Eliminar dirección
    @DeleteMapping("/{idDireccion}")
    public ResponseEntity<Void> delete(@PathVariable Long idDireccion) {
        if (direccionRepository.existsById(idDireccion)) {
            direccionRepository.deleteById(idDireccion);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

