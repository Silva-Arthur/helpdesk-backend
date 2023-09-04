package com.devarthursilva.helpdesk.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	@Value("${jwt.expiration}") //obtem uma propriedade
	private Long expiration;
	
	@Value("${jwt.secret}") //obtem uma propriedade
	private String secret;

	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email) //usuario
				.setExpiration(new Date(System.currentTimeMillis() + expiration)) //tempo de expiracao
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()) //criptografia
				.compact();
	}
}
