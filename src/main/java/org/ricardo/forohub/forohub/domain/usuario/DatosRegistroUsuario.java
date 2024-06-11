package org.ricardo.forohub.forohub.domain.usuario;

import java.util.List;

import org.ricardo.forohub.forohub.domain.perfil.Perfil;

public record DatosRegistroUsuario(
		
		String nombre,
		 String correoElectronico,
		 String contrasena,
		List<Perfil> perfiles
		) {

}
