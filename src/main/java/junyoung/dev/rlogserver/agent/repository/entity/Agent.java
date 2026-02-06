package junyoung.dev.rlogserver.agent.repository.entity;

import java.time.LocalDateTime;
import java.util.UUID;

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

	@Column(name = "agent_uuid", nullable = false, unique = true)
	private UUID agentUuid;

	@Column(name = "name", nullable = false, length = 150)
	private String name;

	@Column(name = "sequence", nullable = false)
	private Integer sequence;

	@Column(name = "hostname")
	private String hostname;

	@Column(name = "os", length = 100)
	private String os;

	@Column(name = "os_version", length = 100)
	private String osVersion;

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
		String name,
		Integer sequence,
		String hostname,
		String os,
		String osVersion,
		String ip
	) {
		return Agent.builder()
			.projectId(projectId)
			.agentUuid(UUID.randomUUID())
			.name(name)
			.sequence(sequence)
			.hostname(hostname)
			.os(os)
			.osVersion(osVersion)
			.ip(ip)
			.status(AgentStatus.REGISTERED)
			.build();
	}

	public void updateInfo(String hostname, String os, String osVersion, String ip) {
		this.hostname = hostname;
		this.os = os;
		this.osVersion = osVersion;
		this.ip = ip;
	}

	public void updateName(String name) {
		this.name = name;
	}
}
