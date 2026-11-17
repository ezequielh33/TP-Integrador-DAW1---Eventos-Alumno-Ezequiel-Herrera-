package ar.edu.undec.integrador.dto;

import jakarta.validation.constraints.NotNull;

public record CrearEntradaRequest(
        @NotNull(message = "El evento es obligatorio")
        Long eventoId,

        @NotNull(message = "El asistente es obligatorio")
        Long asistenteId
) {}