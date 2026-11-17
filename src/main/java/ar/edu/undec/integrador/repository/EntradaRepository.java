
package ar.edu.undec.integrador.repository;

import ar.edu.undec.integrador.entity.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    List<Entrada> findByEventoId(Long eventoId);

    boolean existsByEventoIdAndAsistenteIdAndEstadoNot(
            Long eventoId,
            Long asistenteId,
            Entrada.Estado estado
    );

    long countByEventoIdAndEstadoNot(Long eventoId, Entrada.Estado estado);

    boolean existsByEventoId(Long eventoId);
}
