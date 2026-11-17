
package ar.edu.undec.integrador.controller;

import ar.edu.undec.integrador.entity.Asistente;
import ar.edu.undec.integrador.service.AsistenteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/asistentes")
public class AsistenteController {

    private final AsistenteService asistenteService;

    public AsistenteController(AsistenteService asistenteService) {
        this.asistenteService = asistenteService;
    }

    @GetMapping
    public List<Asistente> listar() {
        return asistenteService.listar();
    }

    @GetMapping("/{id}")
    public Asistente buscar(@PathVariable Long id) {
        return asistenteService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Asistente> crear(@Valid @RequestBody Asistente asistente) {
        Asistente creado = asistenteService.crear(asistente);

        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity.created(ubicacion).body(creado);
    }

    @PutMapping("/{id}")
    public Asistente actualizar(@PathVariable Long id,
                                @Valid @RequestBody Asistente asistente) {
        return asistenteService.actualizar(id, asistente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        asistenteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
