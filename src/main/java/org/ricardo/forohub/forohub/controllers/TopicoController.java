package org.ricardo.forohub.forohub.controllers;

import java.net.URI;
import java.util.Optional;

import org.ricardo.forohub.forohub.domain.topico.DatosActualizarTopico;
import org.ricardo.forohub.forohub.domain.topico.DatosEntradaTopico;
import org.ricardo.forohub.forohub.domain.topico.DatosListarTopico;
import org.ricardo.forohub.forohub.domain.topico.Topico;
import org.ricardo.forohub.forohub.domain.topico.TopicoRepository;
import org.ricardo.forohub.forohub.domain.topico.TopicoService;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

	@Autowired
	private TopicoService topicoService;

	@Autowired
	private TopicoRepository topicoRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DatosListarTopico> registarTopico(@RequestBody @Valid DatosEntradaTopico datosEntradaTopico,
			UriComponentsBuilder uriComponentsBuilder) {
		Topico topico = topicoService.guardar(datosEntradaTopico);
		DatosListarTopico datosListarTopico = new DatosListarTopico(topico.getId(), topico.getTitulo(),
				topico.getMensaje(), topico.getFechaCreacion(),topico.getStatus(), topico.getAutor().getNombre(),topico.getCurso().getNombre());
		URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(datosListarTopico);
	}

	@PutMapping()
	@Transactional
	public ResponseEntity<DatosListarTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico,
			UriComponentsBuilder uriComponentsBuilder) {
		Optional<Topico> topico = Optional.ofNullable(topicoRepository.getReferenceById(datosActualizarTopico.id()));
		if (!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Topico topicoActualizar = topico.get();
		topicoActualizar.actualizarTopico(datosActualizarTopico);
		DatosListarTopico datosListarTopico = new DatosListarTopico(topicoActualizar.getId(),
				topicoActualizar.getTitulo(), topicoActualizar.getMensaje(), topicoActualizar.getFechaCreacion(),topicoActualizar.getStatus(), topicoActualizar.getAutor().getNombre(),topicoActualizar.getCurso().getNombre());
		URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoActualizar.getId()).toUri();
		return ResponseEntity.created(uri).body(datosListarTopico);
	}

	@GetMapping
	public ResponseEntity<?> consultar(
			@PageableDefault(size = 10, sort = { "fechaCreacion" }, direction = Direction.ASC) Pageable paginacion) {
		return ResponseEntity.ok(topicoRepository.findByStatusTrue(paginacion).stream()
				.map(topico -> new DatosListarTopico(topico.getId(), topico.getTitulo(),
						topico.getMensaje(), topico.getFechaCreacion(),topico.getStatus(), topico.getAutor().getNombre(),topico.getCurso().getNombre())));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> retornarTopico(@PathVariable Long id) {
		Optional<Topico> topico = Optional.ofNullable(topicoRepository.getReferenceById(id));
		if (!topico.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		DatosListarTopico datosListarTopico = new DatosListarTopico(topico.get().getId(), topico.get().getTitulo(),
				topico.get().getMensaje(), topico.get().getFechaCreacion(),topico.get().getStatus(), topico.get().getAutor().getNombre(),topico.get().getCurso().getNombre());
		return ResponseEntity.ok(datosListarTopico);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		var topico = topicoRepository.getReferenceById(id);
		topico.eliminar();
		return ResponseEntity.noContent().build();
	}

}
