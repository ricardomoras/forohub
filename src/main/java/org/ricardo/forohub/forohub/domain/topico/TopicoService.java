package org.ricardo.forohub.forohub.domain.topico;


import java.util.Optional;

import org.ricardo.forohub.forohub.domain.curso.Curso;
import org.ricardo.forohub.forohub.domain.curso.CursoRepository;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;
import org.ricardo.forohub.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ValidationException;

@Service
public class TopicoService {

	@Autowired 
	UsuarioRepository usuarioRepository;
	
	@Autowired 
	CursoRepository cursoRepository;
	
	@Autowired
	TopicoRepository topicoRepository;
	
	public Topico guardar(DatosEntradaTopico  datos) {
		Long number = Long.parseLong(datos.idUsuario().toString());
		Optional<Usuario> usuario = usuarioRepository.findById(number);
		
		if (usuario == null) {
			throw new ValidationException("este id para el usuario no fue encontrado");
		}
		
		Curso curso =  cursoRepository.findByNombre(datos.nombreCurso());
		if(curso == null) {
			curso = cursoRepository.save(new Curso(datos.nombreCurso()));
		}
		Topico topico = topicoRepository.findByTituloIgnoreCase(datos.titulo());
		
		if(topico != null) {
			throw new ValidationException("Titulo repetido");
		}
		topico = topicoRepository.findByMensajeIgnoreCase(datos.mensaje());
		if(topico != null) {
			throw new ValidationException("Mensaje repetido");
		}
		DatosRegistroTopico datosRegistroTopico = new DatosRegistroTopico(datos.titulo(), datos.mensaje(), usuario.get(), curso);
		topico = topicoRepository.save(new Topico(datosRegistroTopico));
		
		return topico;
	}
	
}
