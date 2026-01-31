package junyoung.dev.rlogserver.global.grpc;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AgentJwtTokenProvider {

	private final SecretKey secretKey;
	private final long expirationTime;

	public AgentJwtTokenProvider(@Value("${jwt.agent.secret}") String secret, @Value("${jwt.agent.expiration}") long accessExpirationTime) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationTime = accessExpirationTime;
	}

	public String generateToken(Long agentId) {
		Date now = new Date();

		return Jwts.builder()
			.subject(String.valueOf(agentId))
			.issuedAt(now)
			.expiration(new Date(now.getTime() + expirationTime))
			.signWith(secretKey)
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public Long getAgentId(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();

		return Long.parseLong(claims.getSubject());
	}

	public boolean isTokenExpired(String token) {
		try {
			Claims claims = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();

			return claims.getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return true;
		}
	}
}
