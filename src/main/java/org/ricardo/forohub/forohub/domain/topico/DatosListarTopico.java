package org.ricardo.forohub.forohub.domain.topico;

import java.time.LocalDateTime;


public record DatosListarTopico(
		Long id,

		String titulo,

		String mensaje,

		LocalDateTime fechaCreacion,
		Boolean status,
		String autor,
		String curso) {

}
