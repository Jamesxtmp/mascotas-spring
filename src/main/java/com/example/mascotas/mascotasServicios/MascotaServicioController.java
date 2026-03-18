package com.example.mascotas.mascotasServicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mascotas.mascotas.MascotaRepository;
import com.example.mascotas.servicios.ServicioRepository;
import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.servicios.Servicio;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mascotas-servicio")
public class MascotaServicioController {
    @Autowired
    private MascotaServicioRepository mascotaServicioRepository;
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private ServicioRepository servicioRepository;

    @PostMapping("/asignar")
    public ResponseEntity<MascotaServicio> asignarServicio(
            @RequestParam Long mascotaId,
            @RequestParam Long servicioId,
            @RequestParam(required = false) String nota) {
        Mascota mascota = mascotaRepository.findById(mascotaId).orElse(null);
        Servicio servicio = servicioRepository.findById(servicioId).orElse(null);
        if (mascota == null || servicio == null) {
            return ResponseEntity.notFound().build();
        }
        MascotaServicio ms = new MascotaServicio();
        ms.setMascota(mascota);
        ms.setServicio(servicio);
        ms.setFecha(new java.util.Date());
        ms.setNota(nota);
        MascotaServicio guardado = mascotaServicioRepository.save(ms);
        return ResponseEntity.ok(guardado);
    }

    @GetMapping
    public ResponseEntity<List<MascotaServicio>> getAll() {
        List<MascotaServicio> lista = new ArrayList<>();
        mascotaServicioRepository.findAll().forEach(lista::add);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaServicio> getById(@PathVariable Long id) {
        return mascotaServicioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaServicio> update(
            @PathVariable Long id,
            @RequestParam Long mascotaId,
            @RequestParam Long servicioId,
            @RequestParam(required = false) String nota) {
        MascotaServicio ms = mascotaServicioRepository.findById(id).orElse(null);
        if (ms == null) {
            return ResponseEntity.notFound().build();
        }
        Mascota mascota = mascotaRepository.findById(mascotaId).orElse(null);
        Servicio servicio = servicioRepository.findById(servicioId).orElse(null);
        if (mascota == null || servicio == null) {
            return ResponseEntity.notFound().build();
        }
        ms.setMascota(mascota);
        ms.setServicio(servicio);
        ms.setNota(nota);
        ms.setFecha(new java.util.Date());
        MascotaServicio actualizado = mascotaServicioRepository.save(ms);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!mascotaServicioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mascotaServicioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
