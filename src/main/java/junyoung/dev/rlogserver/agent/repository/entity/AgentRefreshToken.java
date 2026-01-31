package junyoung.dev.rlogserver.agent.repository.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agent_refresh_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgentRefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id", nullable = false)
	private Agent agent;

	@Column(name = "token_hash", nullable = false, unique = true)
	private String tokenHash;

	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private AgentTokenStatus status;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public static AgentRefreshToken create(Agent agent, String tokenHash) {
		LocalDateTime expiresAt = LocalDateTime.now().plusDays(7);
		return AgentRefreshToken.builder()
			.agent(agent)
			.tokenHash(tokenHash)
			.expiresAt(expiresAt)
			.status(AgentTokenStatus.ACTIVE)
			.build();
	}

	public void updateTokenHash(String newRefreshTokenHash) {
		this.tokenHash = newRefreshTokenHash;
		this.expiresAt = LocalDateTime.now().plusDays(7);
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expiresAt);
	}

	public boolean isActive() {
		return status == AgentTokenStatus.ACTIVE && !isExpired();
	}

	public void revoke() {
		this.status = AgentTokenStatus.REVOKED;
	}

	public void expire() {
		this.status = AgentTokenStatus.EXPIRED;
	}
}
