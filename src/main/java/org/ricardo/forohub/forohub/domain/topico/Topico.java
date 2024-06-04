package org.ricardo.forohub.forohub.domain.topico;

import java.time.LocalDateTime;
import java.util.List;

import org.ricardo.forohub.forohub.domain.curso.Curso;
import org.ricardo.forohub.forohub.domain.respuesta.Respuesta;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
		
	private Long id;
	private String titulo;
	private String mensaje;
	private LocalDateTime fechaDeCreacion = LocalDateTime.now();
	private Boolean status;
	private List<Usuario> autor;
	private List<Curso> curso;
	private List<Respuesta> respuestas;
	
}
