package junyoung.dev.rlogserver.agent.repository.entity;

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
@Table(name = "agent_access_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgentAccessHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ip", length = 45)
	private String ip;

	@Column(name = "hostname")
	private String hostname;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private AgentAccessStatus status;

	@Column(name = "reason")
	private String reason;

	@CreationTimestamp
	@Column(name = "access_time", nullable = false, updatable = false)
	private LocalDateTime accessTime;

	public static AgentAccessHistory create(
		String ip,
		String hostname,
		AgentAccessStatus status,
		String reason
	) {
		return AgentAccessHistory.builder()
				.ip(ip)
				.hostname(hostname)
				.status(status)
				.reason(reason)
				.build();
	}
}
