
package ar.edu.undec.integrador.controller;

import ar.edu.undec.integrador.entity.Evento;
import ar.edu.undec.integrador.entity.Entrada;
import ar.edu.undec.integrador.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping
    public List<Evento> listar(@RequestParam(required = false) String nombre) {
        return eventoService.listar(nombre);
    }

    @GetMapping("/{id}")
    public Evento buscar(@PathVariable Long id) {
        return eventoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Evento> crear(@Valid @RequestBody Evento evento) {
        Evento creado = eventoService.crear(evento);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(ubicacion).body(creado);
    }

    @PutMapping("/{id}")
    public Evento actualizar(@PathVariable Long id,
                             @Valid @RequestBody Evento evento) {
        return eventoService.actualizar(id, evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/entradas")
    public List<Entrada> listarEntradas(@PathVariable Long id) {
        return eventoService.listarEntradas(id);
    }
}
