package org.ricardo.forohub.forohub.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.ricardo.forohub.forohub.domain.usuario.Usuario;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class TokenService {
	//@Value("${api.security.secret}")
	private String apiSecret = "12345";

	public String generarToken(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(apiSecret); // Validando Firma
			return JWT.create().withIssuer("Foro Hub").withSubject(usuario.getCorreoElectronico()).withClaim("id", usuario.getId())
					.withExpiresAt(geneararFechaExpiracion()).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException();
		}
	}

	public String getSubject(String token) {
		if(token == null) {
			throw new RuntimeException();
		}
		
		DecodedJWT verifier = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256(apiSecret);
			verifier = JWT.require(algorithm).withIssuer("Foro Hub").build().verify(token);
			verifier.getSubject();
		} catch (JWTVerificationException exception) {
			System.out.println(exception.toString());
		}
		if (verifier.getSubject() == null ) {
			throw new RuntimeException("Verifer invalido");
		}
		return verifier.getSubject();
	}

	private Instant geneararFechaExpiracion() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
	}

}
