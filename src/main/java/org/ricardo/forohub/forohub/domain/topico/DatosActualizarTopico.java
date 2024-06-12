package org.ricardo.forohub.forohub.domain.topico;

import org.ricardo.forohub.forohub.domain.curso.Curso;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
		@NotNull
		Long id,
		String titulo, 
		String mensaje, 
		String idUsuario,
		Curso nombreCurso) {

}
