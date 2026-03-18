package com.example.mascotas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    public ResponseEntity<Iterable<Servicio>> findAll() {
        return ResponseEntity.ok(servicioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> findById(@PathVariable Long id) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(id);
        return servicioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Servicio> create(@RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioRepository.save(servicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> update(@PathVariable Long id, @RequestBody Servicio servicioDetails) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(id);
        if (servicioOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Servicio servicio = servicioOptional.get();
        servicio.setDescripcion(servicioDetails.getDescripcion());
        servicio.setPrecio(servicioDetails.getPrecio());
        return ResponseEntity.ok(servicioRepository.save(servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!servicioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servicioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}