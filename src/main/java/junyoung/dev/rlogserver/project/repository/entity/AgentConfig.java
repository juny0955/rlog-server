package junyoung.dev.rlogserver.project.repository.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agent_config")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AgentConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false, unique = true)
	private Project project;

	@Column(name = "timezone", nullable = false)
	private String timezone;

	@Column(name = "batch_size", nullable = false)
	private Long batchSize;

	@Column(name = "flush_interval_sec", nullable = false)
	private Long flushIntervalSec;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "agentConfig", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AgentConfigSource> sources;

	public static AgentConfig defaultConfig(Project project, String timezone) {
		return AgentConfig.builder()
			.project(project)
			.timezone(timezone)
			.batchSize(1000L)
			.flushIntervalSec(15L)
			.build();
	}

	public void updateConfig(Long batchSize, Long flushIntervalSec) {
		this.batchSize = batchSize;
		this.flushIntervalSec = flushIntervalSec;
	}

	public void addSource(String label, String path) {
		this.sources.add(AgentConfigSource.create(this, label, path));
	}
}
