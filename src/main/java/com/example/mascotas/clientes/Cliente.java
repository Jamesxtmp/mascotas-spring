package com.example.mascotas.clientes;

import lombok.*;
import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "cliente")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column(nullable = false, length = 50)
    private String nombre;
    @Column(nullable = false, length = 50)
    private String apePaterno;
    @Column(nullable = false, length = 50)
    private String apeMaterno;
    @Column(nullable = false, length = 50)
    private String email;
}
