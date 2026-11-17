
package ar.edu.undec.integrador.service;

import ar.edu.undec.integrador.entity.Asistente;
import ar.edu.undec.integrador.repository.AsistenteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import ar.edu.undec.integrador.exception.RecursoNoEncontradoException;

@Service
public class AsistenteService {

    private final AsistenteRepository asistenteRepository;

    public AsistenteService(AsistenteRepository asistenteRepository) {
        this.asistenteRepository = asistenteRepository;
    }

    public List<Asistente> listar() {
        return asistenteRepository.findAll();
    }

    public Asistente buscarPorId(Long id) {
        return asistenteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistente no encontrado"));
    }

    public Asistente crear(Asistente asistente) {
        asistente.setId(null);
        return asistenteRepository.save(asistente);
    }

    public Asistente actualizar(Long id, Asistente datos) {
        Asistente asistente = buscarPorId(id);
        asistente.setNombre(datos.getNombre());
        asistente.setEmail(datos.getEmail());
        return asistenteRepository.save(asistente);
    }

    public void eliminar(Long id) {
        Asistente asistente = buscarPorId(id);
        asistenteRepository.delete(asistente);
    }
}
