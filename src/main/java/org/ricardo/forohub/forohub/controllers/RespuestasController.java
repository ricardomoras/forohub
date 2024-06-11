package org.ricardo.forohub.forohub.controllers;


import java.net.URI;
import java.util.Optional;

import org.ricardo.forohub.forohub.domain.respuesta.DatosActualizarRespuesta;
import org.ricardo.forohub.forohub.domain.respuesta.DatosEntradaRespuesta;
import org.ricardo.forohub.forohub.domain.respuesta.DatosListarRespuesta;
import org.ricardo.forohub.forohub.domain.respuesta.DatosRegistroRespuesta;
import org.ricardo.forohub.forohub.domain.respuesta.Respuesta;
import org.ricardo.forohub.forohub.domain.respuesta.RespuestaRepository;
import org.ricardo.forohub.forohub.domain.respuesta.RespuestaService;
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
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestasController {
	
	@Autowired
	RespuestaService respuestaService;
	
	@Autowired
	RespuestaRepository respuestaRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<DatosListarRespuesta> registarTopico(@RequestBody @Valid DatosEntradaRespuesta datosEntradaRespuestas,
			UriComponentsBuilder uriComponentsBuilder) {
		respuestaService.guardar(datosEntradaRespuestas);
		DatosRegistroRespuesta regitroNuevo = respuestaService.guardar(datosEntradaRespuestas);
		Respuesta respuesta = new Respuesta(regitroNuevo);
		respuestaRepository.save(respuesta);
		DatosListarRespuesta datosListarRespuesta = new DatosListarRespuesta(respuesta.getId(),respuesta.getMensaje(),
				respuesta.getTopico().getTitulo(),respuesta.getAutor().getNombre().toString(),respuesta.getSolucion(),respuesta.getFechaDeCreacion());
		URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
		return ResponseEntity.created(uri).body(datosListarRespuesta);
	}
	
	@PutMapping()
	@Transactional
	public ResponseEntity<DatosListarRespuesta> actualizarUsuario(@RequestBody DatosActualizarRespuesta datosActualizarRespuesta,
			UriComponentsBuilder uriComponentsBuilder) {
		Optional<Respuesta> resOpt = Optional.ofNullable(respuestaRepository.getReferenceById(datosActualizarRespuesta.id()));
		if (!resOpt.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Respuesta respuesta = resOpt.get();
		respuesta.actualizarTopico(datosActualizarRespuesta);
		DatosListarRespuesta datosListarRespuesta = new DatosListarRespuesta(respuesta.getId(),respuesta.getMensaje(),
				respuesta.getTopico().getTitulo(),respuesta.getAutor().getNombre().toString(),respuesta.getSolucion(),respuesta.getFechaDeCreacion());
		URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
		return ResponseEntity.created(uri).body(datosListarRespuesta);
	}
	
	@GetMapping
	public ResponseEntity<?> consultar(
			@PageableDefault(sort = { "id" }, direction = Direction.DESC) Pageable paginacion) {
		return ResponseEntity.ok(respuestaRepository.findAll(paginacion).stream()
				.map(t -> new DatosListarRespuesta(t.getId(),t.getMensaje(),t.getTopico().getTitulo(),t.getAutor().getNombre().toString(),t.getSolucion(),t.getFechaDeCreacion())));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> retornarUsuario(@PathVariable Long id) {
		Optional<Respuesta> respuesta = Optional.ofNullable(respuestaRepository.getReferenceById(id));
		if (!respuesta.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		DatosListarRespuesta datosListarUsuario = new DatosListarRespuesta(respuesta.get().getId(), respuesta.get().getMensaje(),
				respuesta.get().getTopico().getTitulo(),respuesta.get().getAutor().getNombre(), respuesta.get().getSolucion(),respuesta.get().getFechaDeCreacion());
		return ResponseEntity.ok(datosListarUsuario);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		Optional<Respuesta> respuesta = respuestaRepository.findById(id);
		System.out.println(respuesta);
		if(!respuesta.isPresent()) {
			throw new ValidationException("este id para la respuesta no fue encontrado");
		}
		if (respuesta.get().getAutor() != null) {
			respuesta.get().setAutor(null);
        }
		if (respuesta.get().getTopico() != null) {
			respuesta.get().setTopico(null);
        }
		respuestaRepository.delete(respuesta.get());
		return ResponseEntity.noContent().build();
	}
}
