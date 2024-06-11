package org.ricardo.forohub.forohub.domain.topico;

import org.ricardo.forohub.forohub.domain.curso.Curso;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public record DatosRegistroTopico(
		String titulo, 
		String mensaje,
		@JsonManagedReference
		Usuario idUsuario,
		Curso nombreCurso
		) {

}
