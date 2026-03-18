package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.ClienteRepository;
import com.example.mascotas.clientes.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/mascotas")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<Iterable<Mascota>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> findById(@PathVariable Long id) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(id);
        return mascotaOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mascota> create(@RequestBody Mascota mascota) {
        if (mascota.getCliente() == null || mascota.getCliente().getIdCliente() == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Cliente> clienteOptional = clienteRepository.findById(mascota.getCliente().getIdCliente());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        mascota.setCliente(clienteOptional.get());
        return ResponseEntity.ok(mascotaRepository.save(mascota));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> update(@PathVariable Long id, @RequestBody Mascota mascotaDetails) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(id);
        if (mascotaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Mascota mascota = mascotaOptional.get();
        mascota.setNombre(mascotaDetails.getNombre());
        mascota.setSexo(mascotaDetails.getSexo());
        mascota.setTipo(mascotaDetails.getTipo());
        mascota.setEdad(mascotaDetails.getEdad());
        mascota.setEnPeligro(mascotaDetails.isEnPeligro());

        if (mascotaDetails.getCliente() != null && mascotaDetails.getCliente().getIdCliente() != null) {
            Optional<Cliente> clienteOptional = clienteRepository.findById(mascotaDetails.getCliente().getIdCliente());
            clienteOptional.ifPresent(mascota::setCliente);
        }

        return ResponseEntity.ok(mascotaRepository.save(mascota));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!mascotaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mascotaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}