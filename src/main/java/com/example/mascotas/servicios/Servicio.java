package com.example.mascotas.servicios;

import com.example.mascotas.mascotasServicios.MascotaServicio;
import lombok.*;
import jakarta.persistence.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "servicio")

public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;
    @Column(nullable = false, length = 260)
    private String descripcion;
    @Column(scale = 2)
    private float precio;

    @OneToMany(mappedBy = "servicio")
    private List<MascotaServicio> mascotaServicio;
}
