package ar.edu.undec.integrador.dto;

import ar.edu.undec.integrador.entity.Entrada;
import jakarta.validation.constraints.NotNull;

public record CambiarEstadoRequest(
        @NotNull(message = "El estado es obligatorio")
        Entrada.Estado estado
) {
}