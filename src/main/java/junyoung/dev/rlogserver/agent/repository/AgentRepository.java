package junyoung.dev.rlogserver.agent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	Optional<Agent> findByProjectIdAndHostname(Long projectId, String hostname);

	boolean existsByProjectIdAndHostname(Long projectId, String hostname);
}
