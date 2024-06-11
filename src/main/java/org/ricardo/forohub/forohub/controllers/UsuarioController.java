package org.ricardo.forohub.forohub.controllers;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;

import org.ricardo.forohub.forohub.domain.usuario.DatosActualizarUsuario;
import org.ricardo.forohub.forohub.domain.usuario.DatosListarUsuario;
import org.ricardo.forohub.forohub.domain.usuario.DatosRegistroUsuario;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;
import org.ricardo.forohub.forohub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping
	public ResponseEntity<?> registarUsuario(@RequestBody DatosRegistroUsuario datosRegistroUsuario,
			UriComponentsBuilder uriComponentsBuilder) {
		Usuario usuario = usuarioRepository.save(new Usuario(datosRegistroUsuario));
		DatosListarUsuario datosListarUsuario = new DatosListarUsuario(usuario.getId(), usuario.getNombre(),
				usuario.getCorreoElectronico());
		URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
		return ResponseEntity.created(uri).body(datosListarUsuario);
	}
	
	@PutMapping()
	@Transactional
	public ResponseEntity<DatosListarUsuario> actualizarUsuario(@RequestBody DatosActualizarUsuario datosActualizarUsuario,
			UriComponentsBuilder uriComponentsBuilder) {
		Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.getReferenceById(datosActualizarUsuario.id()));
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Usuario usuarioActualizar = usuario.get();
		usuarioActualizar.actualizarTopico(datosActualizarUsuario);
		DatosListarUsuario datosListarTopico = new DatosListarUsuario(usuarioActualizar.getId(),
				usuarioActualizar.getNombre(), usuarioActualizar.getCorreoElectronico());
		URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuarioActualizar.getId()).toUri();
		return ResponseEntity.created(uri).body(datosListarTopico);
	}
	@GetMapping
	public ResponseEntity<?> consultar(
			@PageableDefault(sort = { "id" }, direction = Direction.DESC) Pageable paginacion) {
		Stream<Object> usuarios = usuarioRepository.findAll().stream().map(u -> new DatosListarUsuario(u.getId(), u.getNombre(), u.getCorreoElectronico()));
		return ResponseEntity.ok(usuarios);
	}
	

	@GetMapping("/{id}")
	public ResponseEntity<?> retornarUsuario(@PathVariable Long id) {
		Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.getReferenceById(id));
		if (!usuario.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		DatosListarUsuario datosListarUsuario = new DatosListarUsuario(usuario.get().getId(), usuario.get().getNombre(),
				usuario.get().getContrasena());
		return ResponseEntity.ok(datosListarUsuario);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		var usuario = usuarioRepository.findById(id);
		if(usuario.isPresent()) {
			usuarioRepository.delete(usuario.get());
		}
		
		return ResponseEntity.noContent().build();
	}
		
}
