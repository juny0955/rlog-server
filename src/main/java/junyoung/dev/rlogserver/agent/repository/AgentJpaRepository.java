package junyoung.dev.rlogserver.agent.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import junyoung.dev.rlogserver.agent.repository.entity.Agent;

public interface AgentJpaRepository extends JpaRepository<Agent, Long> {
	Optional<Agent> findByAgentUuid(UUID agentUuid);

	@Query("SELECT MAX(a.sequence) FROM Agent a WHERE a.projectId = :projectId")
	Optional<Integer> findMaxSequenceByProjectId(@Param("projectId") Long projectId);

	boolean existsByName(String name);
}
