package org.ricardo.forohub.forohub.domain.respuesta;

import java.time.LocalDateTime;

import org.ricardo.forohub.forohub.domain.topico.Topico;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensaje;
	@ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
	@JsonBackReference
	private Topico topico;
	private LocalDateTime fechaDeCreacion = LocalDateTime.now();
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
	@JsonBackReference
	private Usuario autor;
	private String solucion;

	public Respuesta(DatosRegistroRespuesta datosEntradaRespuestas) {

		this.mensaje = datosEntradaRespuestas.mensaje();
		this.topico = datosEntradaRespuestas.topico();
		this.autor = datosEntradaRespuestas.autor();
		this.solucion = datosEntradaRespuestas.solucion();
		
	}

	public void actualizarTopico(DatosActualizarRespuesta datosActualizarRespuesta) {
		if(datosActualizarRespuesta.mensaje() != null) {
			this.mensaje = datosActualizarRespuesta.mensaje();
		}
		if(datosActualizarRespuesta.solucion() != null) {
			this.solucion = datosActualizarRespuesta.solucion() ;
		}
		
	}
}
