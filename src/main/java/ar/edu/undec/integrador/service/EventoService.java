
package ar.edu.undec.integrador.service;

import ar.edu.undec.integrador.entity.Evento;
import ar.edu.undec.integrador.entity.Entrada;
import ar.edu.undec.integrador.repository.EventoRepository;
import ar.edu.undec.integrador.repository.EntradaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import ar.edu.undec.integrador.exception.RecursoNoEncontradoException;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EntradaRepository entradaRepository;

    public EventoService(EventoRepository eventoRepository,
                         EntradaRepository entradaRepository) {
        this.eventoRepository = eventoRepository;
        this.entradaRepository = entradaRepository;
    }

    public List<Evento> listar(String nombre) {
        if (nombre != null && !nombre.isBlank()) {
            return eventoRepository.findByNombreContainingIgnoreCase(nombre);
        }
        return eventoRepository.findAll();
    }

    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado"));
    }

    public Evento crear(Evento evento) {
        evento.setId(null);
        return eventoRepository.save(evento);
    }

    public Evento actualizar(Long id, Evento datos) {
        Evento evento = buscarPorId(id);
        evento.setNombre(datos.getNombre());
        evento.setFecha(datos.getFecha());
        evento.setCapacidad(datos.getCapacidad());
        evento.setPrecio(datos.getPrecio());
        return eventoRepository.save(evento);
    }

    @Transactional
    public void eliminar(Long id) {
        Evento evento = buscarPorId(id);

        if (entradaRepository.existsByEventoId(id)) {
            throw new IllegalStateException("No se puede eliminar un evento que tiene entradas");
        }

        eventoRepository.delete(evento);
    }

    public List<Entrada> listarEntradas(Long id) {
        buscarPorId(id);
        return entradaRepository.findByEventoId(id);
    }
}
