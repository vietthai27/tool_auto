package com.fis.tool.JWT;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private static final int expireInMs = 6000 * 100000;

	private final static String key = "secret";

	public String generate(String username) {
		return Jwts.builder().setSubject(username).setIssuer("thai27").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expireInMs))
				.signWith(SignatureAlgorithm.HS256, key).compact();
	}

	public boolean validate(String token) {
		if (getUsername(token) != null && isExpired(token)) {
			return true;
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		return claims.getSubject();
	}

	public boolean isExpired(String token) {
		Claims claims = getClaims(token);
		return claims.getExpiration().after(new Date(System.currentTimeMillis()));
	}

	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}

}
