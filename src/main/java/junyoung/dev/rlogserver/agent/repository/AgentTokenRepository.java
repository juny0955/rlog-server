package junyoung.dev.rlogserver.agent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentTokenRepository extends JpaRepository<AgentRefreshToken, Long> {

	Optional<AgentRefreshToken> findByTokenHash(String tokenHash);

	Optional<AgentRefreshToken> findByAgentIdAndStatus(Long agentId, AgentTokenStatus status);

	List<AgentRefreshToken> findAllByAgentId(Long agentId);

	@Modifying
	@Query("UPDATE AgentRefreshToken t SET t.status = :status WHERE t.agent.id = :agentId AND t.status = 'ACTIVE'")
	int revokeAllActiveTokensByAgentId(@Param("agentId") Long agentId, @Param("status") AgentTokenStatus status);
}
