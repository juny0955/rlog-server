package junyoung.dev.rlogserver.agent.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import junyoung.dev.rlogserver.agent.repository.entity.Agent;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	List<Agent> findByProjectId(Long projectId);

	Optional<Agent> findByAgentUuid(UUID agentUuid);

	long countByProjectId(Long projectId);

	long countByProjectIdAndStatus(Long projectId, AgentStatus status);

	@Query("SELECT MAX(a.sequence) FROM Agent a WHERE a.projectId = :projectId")
	Optional<Integer> findMaxSequenceByProjectId(@Param("projectId") Long projectId);

	boolean existsByName(String name);
}
