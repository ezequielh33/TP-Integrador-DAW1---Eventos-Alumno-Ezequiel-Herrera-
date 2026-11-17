
package ar.edu.undec.integrador.repository;

import ar.edu.undec.integrador.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findByNombreContainingIgnoreCase(String nombre);
}
