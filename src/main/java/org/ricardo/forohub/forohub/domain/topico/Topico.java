package org.ricardo.forohub.forohub.domain.topico;

import java.time.LocalDateTime;
import java.util.List;

import org.ricardo.forohub.forohub.domain.curso.Curso;
import org.ricardo.forohub.forohub.domain.respuesta.Respuesta;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensaje;
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	private Boolean status = true;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
	@JsonBackReference
	private Usuario autor;
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id")
	private Curso curso;
	@OneToMany(mappedBy = "topico", orphanRemoval = true,fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Respuesta> respuestas;
	
		
	public Topico(DatosRegistroTopico datosRegistroTopico) {
		this.titulo = datosRegistroTopico.titulo();
		this.mensaje = datosRegistroTopico.mensaje();
		this.autor = datosRegistroTopico.idUsuario();
		this.curso = datosRegistroTopico.nombreCurso();
	}


	public void eliminar() {
		this.status = false;
	}


	public void actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
		if(datosActualizarTopico.titulo() != null) {
			this.titulo = datosActualizarTopico.titulo();
		}
		if(datosActualizarTopico.mensaje() != null) {
			this.mensaje = datosActualizarTopico.mensaje();
		}
		
	}
}
