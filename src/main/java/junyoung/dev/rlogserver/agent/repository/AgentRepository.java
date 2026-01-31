package junyoung.dev.rlogserver.agent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import junyoung.dev.rlogserver.agent.repository.entity.Agent;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;

public interface AgentRepository extends JpaRepository<Agent, Long> {
	List<Agent> findByProjectId(Long projectId);

	boolean existsByProjectIdAndHostname(Long projectId, String hostname);

	long countByProjectId(Long projectId);

	long countByProjectIdAndStatus(Long projectId, AgentStatus status);
}
