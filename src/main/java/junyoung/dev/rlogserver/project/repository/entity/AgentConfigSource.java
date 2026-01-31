package junyoung.dev.rlogserver.project.repository.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "agent_config_sources")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgentConfigSource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_config_id", nullable = false)
	private AgentConfig agentConfig;

	@Column(name = "label", nullable = false)
	private String label;

	@Column(name = "path", nullable = false)
	private String path;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public static AgentConfigSource create(AgentConfig agentConfig, String label, String path) {
		return AgentConfigSource.builder()
			.agentConfig(agentConfig)
			.label(label)
			.path(path)
			.enabled(true)
			.build();
	}
}
