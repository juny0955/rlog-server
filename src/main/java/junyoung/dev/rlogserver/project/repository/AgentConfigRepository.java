package junyoung.dev.rlogserver.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import junyoung.dev.rlogserver.project.repository.entity.AgentConfig;

public interface AgentConfigRepository extends JpaRepository<AgentConfig, Long> {

	@Query("SELECT ac FROM AgentConfig ac " +
		"LEFT JOIN FETCH ac.sources " +
		"WHERE ac.project.id = :projectId")
	Optional<AgentConfig> findByProjectIdWithSources(@Param("projectId") Long projectId);
}
