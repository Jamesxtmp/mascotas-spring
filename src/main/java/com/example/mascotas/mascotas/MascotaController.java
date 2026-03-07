package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.ClienteRepository;
import com.example.mascotas.clientes.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mascotas")

public class MascotaController {
    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todas las mascotas
    @GetMapping()
    public ResponseEntity<Iterable<Mascota>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    // Obtener mascota por ID
    @GetMapping("/{idMascota}")
    public ResponseEntity<Mascota> findById(@PathVariable Long idMascota) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);
        if (mascotaOptional.isPresent()) {
            return ResponseEntity.ok(mascotaOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nueva mascota
    @PostMapping
    public ResponseEntity<Mascota> create(
            @RequestBody Mascota mascota,
            UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(mascota.getCliente().getIdCliente());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        mascota.setCliente(clienteOptional.get());
        Mascota mascotaCreada = mascotaRepository.save(mascota);
        URI uri = uriComponentsBuilder.path("/mascotas/{idMascota}").buildAndExpand(
                mascotaCreada.getIdMascota()
        ).toUri();
        return ResponseEntity.created(uri).body(mascotaCreada);
    }

    // Actualizar mascota
    @PutMapping("/{idMascota}")
    public ResponseEntity<Mascota> update(
            @PathVariable Long idMascota,
            @RequestBody Mascota mascotaActualizada) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);
        if (!mascotaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Mascota mascotaExistente = mascotaOptional.get();
        mascotaExistente.setNombre(mascotaActualizada.getNombre());
        mascotaExistente.setSexo(mascotaActualizada.getSexo());
        mascotaExistente.setTipo(mascotaActualizada.getTipo());
        mascotaExistente.setEdad(mascotaActualizada.getEdad());
        mascotaExistente.setEnPeligro(mascotaActualizada.isEnPeligro());

        if (mascotaActualizada.getCliente() != null) {
            Optional<Cliente> clienteOptional = clienteRepository.findById(mascotaActualizada.getCliente().getIdCliente());
            if (clienteOptional.isPresent()) {
                mascotaExistente.setCliente(clienteOptional.get());
            }
        }

        Mascota mascotaUpdate = mascotaRepository.save(mascotaExistente);
        return ResponseEntity.ok(mascotaUpdate);
    }

    // Eliminar mascota
    @DeleteMapping("/{idMascota}")
    public ResponseEntity<Void> delete(@PathVariable Long idMascota) {
        if (mascotaRepository.existsById(idMascota)) {
            mascotaRepository.deleteById(idMascota);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
