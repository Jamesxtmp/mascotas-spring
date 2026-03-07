package com.example.mascotas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/servicio")
public class ServicioController {
    @Autowired
    private ServicioRepository servicioRepository;

    // Obtener todos los servicios
    @GetMapping()
    public ResponseEntity<Iterable<Servicio>> findAll() {
        return ResponseEntity.ok(servicioRepository.findAll());
    }

    // Obtener servicio por ID
    @GetMapping("/{idServicio}")
    public ResponseEntity<Servicio> findById(@PathVariable Long idServicio) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (servicioOptional.isPresent()) {
            return ResponseEntity.ok(servicioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nuevo servicio
    @PostMapping()
    public ResponseEntity<Servicio> create(
            @RequestBody Servicio servicio,
            UriComponentsBuilder uriBuilder) {
        Servicio servicioCreado = servicioRepository.save(servicio);
        URI uri = uriBuilder.path("/servicio/{idServicio}").buildAndExpand(
                servicioCreado.getIdServicio()
        ).toUri();
        return ResponseEntity.created(uri).body(servicioCreado);
    }

    // Actualizar servicio
    @PutMapping("/{idServicio}")
    public ResponseEntity<Servicio> update(
            @PathVariable Long idServicio,
            @RequestBody Servicio servicioActualizado) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(idServicio);
        if (!servicioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Servicio servicioExistente = servicioOptional.get();
        servicioExistente.setDescripcion(servicioActualizado.getDescripcion());
        servicioExistente.setPrecio(servicioActualizado.getPrecio());

        Servicio servicioUpdate = servicioRepository.save(servicioExistente);
        return ResponseEntity.ok(servicioUpdate);
    }

    // Eliminar servicio
    @DeleteMapping("/{idServicio}")
    public ResponseEntity<Void> delete(@PathVariable Long idServicio) {
        if (servicioRepository.existsById(idServicio)) {
            servicioRepository.deleteById(idServicio);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

