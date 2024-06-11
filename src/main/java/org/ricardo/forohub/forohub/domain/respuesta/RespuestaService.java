package org.ricardo.forohub.forohub.domain.respuesta;

import java.util.Optional;

import org.ricardo.forohub.forohub.domain.topico.Topico;
import org.ricardo.forohub.forohub.domain.topico.TopicoRepository;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;
import org.ricardo.forohub.forohub.domain.usuario.UsuarioRepository;
import org.ricardo.forohub.forohub.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RespuestaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	public DatosRegistroRespuesta guardar(DatosEntradaRespuesta datos) {
		Long number = Long.parseLong(datos.idUsuario());
		Optional<Usuario> usuario = usuarioRepository.findById(number);
		if (!usuario.isPresent()) {
			throw new ValidacionDeIntegridad("No existe el usuario");
		}
		number = Long.parseLong(datos.idTopico());
		Optional<Topico> topico = topicoRepository.findById(number);
		if (!topico.isPresent()) {
			throw new ValidacionDeIntegridad("Este topico no fue encontrado");
		}
		DatosRegistroRespuesta registro = new DatosRegistroRespuesta(datos.mensaje(), topico.get(), usuario.get(), datos.solucion());
		
		return registro; 
	}

}
