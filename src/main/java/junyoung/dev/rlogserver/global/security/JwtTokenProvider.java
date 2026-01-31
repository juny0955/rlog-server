package junyoung.dev.rlogserver.global.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import junyoung.dev.rlogserver.user.repository.UserRole;

@Component
public class JwtTokenProvider {

	private final SecretKey secretKey;
	private final long expirationTime;

	public JwtTokenProvider(@Value("${jwt.user.secret}") String secret, @Value("${jwt.user.expiration}") long expirationTime) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationTime = expirationTime;
	}

	public String generateToken(Long userId, String username, UserRole role) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationTime);

		return Jwts.builder()
			.subject(String.valueOf(userId))
			.claim("username", username)
			.claim("role", role.name())
			.issuedAt(now)
			.expiration(expiryDate)
			.signWith(secretKey)
			.compact();
	}

	public Long getUserIdFromToken(String token) {
		Claims claims = parseClaims(token);
		return Long.parseLong(claims.getSubject());
	}

	public String getUsernameFromToken(String token) {
		Claims claims = parseClaims(token);
		return claims.get("username", String.class);
	}

	public UserRole getRoleFromToken(String token) {
		Claims claims = parseClaims(token);
		String roleName = claims.get("role", String.class);
		return UserRole.valueOf(roleName);
	}

	public boolean validateToken(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}
}
