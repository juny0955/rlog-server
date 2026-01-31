package junyoung.dev.rlogserver.project.repository.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "project_key", nullable = false, unique = true)
	private String projectKey;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 20)
	private ProjectStatus status;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

	@OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private AgentConfig agentConfig;

	public static Project create(
		String name,
		String description,
		String projectKey,
		String timezone
	) {
		Project project = Project.builder()
			.name(name)
			.description(description)
			.projectKey(projectKey)
			.status(ProjectStatus.ACTIVE)
			.build();

		project.setConfig(AgentConfig.defaultConfig(project, timezone));
		return project;
	}

	public void setConfig(AgentConfig config) {
		this.agentConfig = config;
	}
}
