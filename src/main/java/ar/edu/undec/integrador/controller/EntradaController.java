package ar.edu.undec.integrador.controller;

import ar.edu.undec.integrador.dto.CambiarEstadoRequest;
import ar.edu.undec.integrador.dto.CrearEntradaRequest;
import ar.edu.undec.integrador.entity.Entrada;
import ar.edu.undec.integrador.service.EntradaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/entradas")
public class EntradaController {

    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @GetMapping
    public List<Entrada> listar() {
        return entradaService.listar();
    }

    @GetMapping("/{id}")
    public Entrada buscar(@PathVariable Long id) {
        return entradaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Entrada> crear(@Valid @RequestBody CrearEntradaRequest request) {
        Entrada creada = entradaService.crear(
                request.eventoId(),
                request.asistenteId()
        );

        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();

        return ResponseEntity.created(ubicacion).body(creada);
    }

    @PatchMapping("/{id}/estado")
    public Entrada cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambiarEstadoRequest request) {

        return entradaService.cambiarEstado(id, request.estado());
    }
}