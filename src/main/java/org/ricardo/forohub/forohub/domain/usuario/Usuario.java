package org.ricardo.forohub.forohub.domain.usuario;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.ricardo.forohub.forohub.domain.perfil.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	@Email
	private String correoElectronico;
	private String contrasena;
	@JsonBackReference
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuario_perfil",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
	private List<Perfil> perfiles;
	
	public Usuario(DatosRegistroUsuario datosRegistroUsuario) {
		this.nombre = datosRegistroUsuario.nombre();
		this.correoElectronico = datosRegistroUsuario.correoElectronico();
		this.contrasena = datosRegistroUsuario.contrasena();
		this.perfiles = datosRegistroUsuario.perfiles().stream().map(p -> new Perfil(p.getNombre())).collect(Collectors.toList());
	}
	
	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
		for (Perfil perfil : perfiles) {
			perfil.getUsuarios().add(this);
		}
	}

	public Usuario(String correoElectronico, String contrasena) {
		this.correoElectronico = correoElectronico;
		this.contrasena = contrasena;
	}
	
	public void actualizarTopico(DatosActualizarUsuario datosActualizarUsuario) {
		if(datosActualizarUsuario.nombre() != null) {
			this.nombre = datosActualizarUsuario.nombre();
		}
		if(datosActualizarUsuario.correoElectronico() != null) {
			this.correoElectronico = datosActualizarUsuario.correoElectronico();
		}
		if(datosActualizarUsuario.contrasena() != null) {
			this.contrasena = datosActualizarUsuario.contrasena();
		}
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}
	@Override
	public String getPassword() {
		return contrasena;
	}
	@Override
	public String getUsername() {
		return correoElectronico;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
