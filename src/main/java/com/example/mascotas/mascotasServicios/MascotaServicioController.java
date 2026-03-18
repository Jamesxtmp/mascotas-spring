package com.example.mascotas.mascotasServicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.mascotas.mascotas.MascotaRepository;
import com.example.mascotas.servicios.ServicioRepository;
import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.servicios.Servicio;

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
}
