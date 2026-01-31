package junyoung.dev.rlogserver.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import junyoung.dev.rlogserver.project.repository.entity.AgentConfig;

public interface AgentConfigRepository extends JpaRepository<AgentConfig, Long> {
	Optional<AgentConfig> findByProjectId(Long projectId);
}
