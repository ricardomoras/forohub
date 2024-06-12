package org.ricardo.forohub.forohub.domain.topico;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosEntradaTopico(
		@NotBlank
		String titulo, 
		@NotBlank
		String mensaje, 
		@NotNull
		String idUsuario,
		@NotBlank
		String nombreCurso) {

}
