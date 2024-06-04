package org.ricardo.forohub.forohub.domain.respuesta;

import java.time.LocalDateTime;
import java.util.List;

import org.ricardo.forohub.forohub.domain.usuario.Usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta {
	
	private Long id;
	private String mensaje;
	private String topico;
	private LocalDateTime fechaDeCreacion = LocalDateTime.now();
	private List<Usuario> autor;
	private String solucion;

}
