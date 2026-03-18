package com.example.mascotas.mascotasServicios;

import java.util.Date;

public class MascotaServicioDTO {
    private Long idMascotaServicio;
    private Date fecha;
    private String nota;
    private Long idMascota;
    private Long idServicio;

    public MascotaServicioDTO(Long idMascotaServicio, Date fecha, String nota, Long idMascota, Long idServicio) {
        this.idMascotaServicio = idMascotaServicio;
        this.fecha = fecha;
        this.nota = nota;
        this.idMascota = idMascota;
        this.idServicio = idServicio;
    }

    public Long getIdMascotaServicio() { return idMascotaServicio; }
    public Date getFecha() { return fecha; }
    public String getNota() { return nota; }
    public Long getIdMascota() { return idMascota; }
    public Long getIdServicio() { return idServicio; }

    public void setIdMascotaServicio(Long idMascotaServicio) { this.idMascotaServicio = idMascotaServicio; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public void setNota(String nota) { this.nota = nota; }
    public void setIdMascota(Long idMascota) { this.idMascota = idMascota; }
    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }
}

