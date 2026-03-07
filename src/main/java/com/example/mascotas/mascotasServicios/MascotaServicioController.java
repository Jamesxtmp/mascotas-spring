package com.example.mascotas.mascotasServicios;

import com.example.mascotas.mascotas.MascotaRepository;
import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.servicios.ServicioRepository;
import com.example.mascotas.servicios.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mascota-servicio")
public class MascotaServicioController {
    @Autowired
    private MascotaServicioRepository mascotaServicioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    // Obtener todos los registros de mascota-servicio
    @GetMapping()
    public ResponseEntity<Iterable<MascotaServicio>> findAll() {
        return ResponseEntity.ok(mascotaServicioRepository.findAll());
    }

    // Obtener registro de mascota-servicio por ID
    @GetMapping("/{idMascotaServicio}")
    public ResponseEntity<MascotaServicio> findById(@PathVariable Long idMascotaServicio) {
        Optional<MascotaServicio> mascotaServicioOptional = mascotaServicioRepository.findById(idMascotaServicio);
        if (mascotaServicioOptional.isPresent()) {
            return ResponseEntity.ok(mascotaServicioOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nuevo registro de mascota-servicio
    @PostMapping()
    public ResponseEntity<MascotaServicio> create(
            @RequestBody MascotaServicio mascotaServicio,
            UriComponentsBuilder uriBuilder) {

        // Validar que la mascota existe
        if (mascotaServicio.getMascota() == null || mascotaServicio.getMascota().getIdMascota() == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(mascotaServicio.getMascota().getIdMascota());
        if (mascotaOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // Validar que el servicio existe
        if (mascotaServicio.getServicio() == null || mascotaServicio.getServicio().getIdServicio() == null) {
            return ResponseEntity.unprocessableEntity().build();
        }
        Optional<Servicio> servicioOptional = servicioRepository.findById(mascotaServicio.getServicio().getIdServicio());
        if (servicioOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        mascotaServicio.setMascota(mascotaOptional.get());
        mascotaServicio.setServicio(servicioOptional.get());

        MascotaServicio mascotaServicioCreado = mascotaServicioRepository.save(mascotaServicio);
        URI uri = uriBuilder.path("/mascota-servicio/{idMascotaServicio}").buildAndExpand(
                mascotaServicioCreado.getIdMascotaServicio()
        ).toUri();
        return ResponseEntity.created(uri).body(mascotaServicioCreado);
    }

    // Actualizar registro de mascota-servicio
    @PutMapping("/{idMascotaServicio}")
    public ResponseEntity<MascotaServicio> update(
            @PathVariable Long idMascotaServicio,
            @RequestBody MascotaServicio mascotaServicioActualizado) {
        Optional<MascotaServicio> mascotaServicioOptional = mascotaServicioRepository.findById(idMascotaServicio);
        if (!mascotaServicioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        MascotaServicio mascotaServicioExistente = mascotaServicioOptional.get();
        mascotaServicioExistente.setFecha(mascotaServicioActualizado.getFecha());
        mascotaServicioExistente.setNota(mascotaServicioActualizado.getNota());

        // Validar y actualizar mascota si se proporciona
        if (mascotaServicioActualizado.getMascota() != null && mascotaServicioActualizado.getMascota().getIdMascota() != null) {
            Optional<Mascota> mascotaOptional = mascotaRepository.findById(mascotaServicioActualizado.getMascota().getIdMascota());
            if (mascotaOptional.isPresent()) {
                mascotaServicioExistente.setMascota(mascotaOptional.get());
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }

        // Validar y actualizar servicio si se proporciona
        if (mascotaServicioActualizado.getServicio() != null && mascotaServicioActualizado.getServicio().getIdServicio() != null) {
            Optional<Servicio> servicioOptional = servicioRepository.findById(mascotaServicioActualizado.getServicio().getIdServicio());
            if (servicioOptional.isPresent()) {
                mascotaServicioExistente.setServicio(servicioOptional.get());
            } else {
                return ResponseEntity.unprocessableEntity().build();
            }
        }

        MascotaServicio mascotaServicioUpdate = mascotaServicioRepository.save(mascotaServicioExistente);
        return ResponseEntity.ok(mascotaServicioUpdate);
    }

    // Eliminar registro de mascota-servicio
    @DeleteMapping("/{idMascotaServicio}")
    public ResponseEntity<Void> delete(@PathVariable Long idMascotaServicio) {
        if (mascotaServicioRepository.existsById(idMascotaServicio)) {
            mascotaServicioRepository.deleteById(idMascotaServicio);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

