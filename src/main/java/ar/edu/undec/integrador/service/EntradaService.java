
package ar.edu.undec.integrador.service;

import ar.edu.undec.integrador.entity.*;
import ar.edu.undec.integrador.repository.*;
import ar.edu.undec.integrador.exception.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final EventoRepository eventoRepository;
    private final AsistenteRepository asistenteRepository;

    public EntradaService(EntradaRepository entradaRepository,
                          EventoRepository eventoRepository,
                          AsistenteRepository asistenteRepository) {
        this.entradaRepository = entradaRepository;
        this.eventoRepository = eventoRepository;
        this.asistenteRepository = asistenteRepository;
    }

    public List<Entrada> listar() {
        return entradaRepository.findAll();
    }

    public Entrada buscarPorId(Long id) {
        return entradaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Entrada no encontrada"));
    }

    @Transactional
    public Entrada crear(Long eventoId, Long asistenteId) {

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Evento no encontrado"));

        Asistente asistente = asistenteRepository.findById(asistenteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistente no encontrado"));

        boolean yaTieneEntrada =
                entradaRepository.existsByEventoIdAndAsistenteIdAndEstadoNot(
                        eventoId, asistenteId, Entrada.Estado.ANULADA);

        if (yaTieneEntrada) {
            throw new IllegalStateException(
                    "El asistente ya tiene una entrada activa para este evento");
        }

        long entradasActivas =
                entradaRepository.countByEventoIdAndEstadoNot(
                        eventoId, Entrada.Estado.ANULADA);

        if (entradasActivas >= evento.getCapacidad()) {
            throw new IllegalStateException("El evento no tiene capacidad disponible");
        }

        Entrada entrada = new Entrada();
        entrada.setEvento(evento);
        entrada.setAsistente(asistente);
        entrada.setEstado(Entrada.Estado.RESERVADA);

        return entradaRepository.save(entrada);
    }

    @Transactional
    public Entrada cambiarEstado(Long id, Entrada.Estado nuevoEstado) {

        Entrada entrada = buscarPorId(id);
        Entrada.Estado estadoActual = entrada.getEstado();

        boolean valido =
                (estadoActual == Entrada.Estado.RESERVADA &&
                        (nuevoEstado == Entrada.Estado.PAGADA ||
                                nuevoEstado == Entrada.Estado.ANULADA))
                        ||
                        (estadoActual == Entrada.Estado.PAGADA &&
                                (nuevoEstado == Entrada.Estado.USADA ||
                                        nuevoEstado == Entrada.Estado.ANULADA));

        if (!valido) {
            throw new IllegalStateException(
                    "No se permite cambiar de " + estadoActual + " a " + nuevoEstado);
        }

        entrada.setEstado(nuevoEstado);
        return entradaRepository.save(entrada);
    }
}
