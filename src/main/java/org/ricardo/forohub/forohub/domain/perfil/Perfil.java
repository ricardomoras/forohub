package org.ricardo.forohub.forohub.domain.perfil;

import java.util.List;

import org.ricardo.forohub.forohub.domain.usuario.Usuario;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Perfil")
@Table(name = "perfiles")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	
	@ManyToMany(mappedBy = "perfiles", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JsonBackReference
	private List<Usuario> usuarios;
	
	public Perfil(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Perfil [nombre=" + nombre + ", usuarios=" + usuarios + "]";
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
		for (Usuario usuario : usuarios) {
			usuario.getPerfiles().add(this);
		}
	}
	
}
