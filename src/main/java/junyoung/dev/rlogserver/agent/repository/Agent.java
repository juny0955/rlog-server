package junyoung.dev.rlogserver.agent.repository;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agents")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "project_id", nullable = false)
	private Long projectId;

	@Column(name = "hostname")
	private String hostname;

	@Column(name = "os", length = 100)
	private String os;

	@Column(name = "ip", length = 45)
	private String ip;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private AgentStatus status;

	@Column(name = "last_seen_at")
	private LocalDateTime lastSeenAt;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public static Agent create(
		Long projectId,
		String hostname,
		String os
		// String ip
	) {
		return Agent.builder()
			.projectId(projectId)
			.hostname(hostname)
			.os(os)
			// .ip(ip)
			.status(AgentStatus.REGISTERED)
			.build();
	}

	public void markOnline() {
		this.status = AgentStatus.ONLINE;
		this.lastSeenAt = LocalDateTime.now();
	}

	public void markOffline() {
		this.status = AgentStatus.OFFLINE;
	}

	public void updateLastSeen() {
		this.lastSeenAt = LocalDateTime.now();
	}
}
