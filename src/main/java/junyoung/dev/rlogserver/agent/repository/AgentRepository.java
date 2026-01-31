package junyoung.dev.rlogserver.agent.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import junyoung.dev.rlogserver.agent.repository.entity.Agent;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	List<Agent> findByProjectId(Long projectId);

	Optional<Agent> findByAgentUuid(UUID agentUuid);

	long countByProjectId(Long projectId);

	long countByProjectIdAndStatus(Long projectId, AgentStatus status);
}
