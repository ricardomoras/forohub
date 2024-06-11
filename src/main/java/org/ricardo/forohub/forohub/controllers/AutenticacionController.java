package org.ricardo.forohub.forohub.controllers;

import org.ricardo.forohub.forohub.domain.usuario.DatosAutenticacionUsuario;
import org.ricardo.forohub.forohub.domain.usuario.Usuario;
import org.ricardo.forohub.forohub.infra.security.DatosJWTToken;
import org.ricardo.forohub.forohub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

//	@Autowired
//	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<?> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
//		Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(), 
//				datosAutenticacionUsuario.clave());
//		var usuarioAutenticado = authenticationManager.authenticate(authToken);
		
		Usuario usuario = new Usuario(datosAutenticacionUsuario.correoElectronico(),datosAutenticacionUsuario.contrasena());
		String JWTtoken = tokenService.generarToken(usuario);
		return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
	}
}

