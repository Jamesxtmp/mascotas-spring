package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.ClienteRepository;
import com.example.mascotas.clientes.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/mascotas")

public class MascotaController {
    @Autowired
    private MascotaRepository mascotaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Mascota>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Mascota> create (@RequestBody Mascota mascota, UriComponentsBuilder uriComponentsBuilder){
        Optional<Cliente> clienteOptional = clienteRepository.findById(mascota.getCliente().getIdCliente());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        mascota.setCliente(clienteOptional.get());
    }
}
