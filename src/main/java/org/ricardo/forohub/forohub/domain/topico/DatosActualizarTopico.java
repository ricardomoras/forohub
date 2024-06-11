package org.ricardo.forohub.forohub.domain.topico;

import org.ricardo.forohub.forohub.domain.curso.Curso;

public record DatosActualizarTopico(
		Long id,
		String titulo, 
		String mensaje, 
		String idUsuario,
		Curso nombreCurso) {

}
