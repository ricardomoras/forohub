package org.ricardo.forohub.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosEntradaRespuesta(
		@NotBlank
		String mensaje,
		@NotNull 
		String idTopico,
		@NotNull 
		String idUsuario,
		@NotBlank 
		String solucion) {

}
