package org.ricardo.forohub.forohub.domain.respuesta;


import org.ricardo.forohub.forohub.domain.topico.Topico;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;


public record DatosRegistroRespuesta(
		String mensaje,
		 Topico topico,
		 Usuario autor,
		 String solucion) {

}
