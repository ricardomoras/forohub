package org.ricardo.forohub.forohub.domain.usuario;

import java.util.List;

import org.ricardo.forohub.forohub.domain.perfil.Perfil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	private Long id;
	private String nombre;
	private String correoElectronico;
	private String contrasena;
	private List<Perfil> perfiles;
}
