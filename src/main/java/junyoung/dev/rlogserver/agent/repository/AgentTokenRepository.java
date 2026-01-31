package junyoung.dev.rlogserver.agent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import junyoung.dev.rlogserver.agent.repository.entity.AgentRefreshToken;

public interface AgentTokenRepository extends JpaRepository<AgentRefreshToken, Long> {

	@Query("SELECT t FROM AgentRefreshToken t " +
		"JOIN FETCH t.agent " +
		"WHERE t.tokenHash = :tokenHash")
	Optional<AgentRefreshToken> findByTokenHashWithAgent(@Param("tokenHash") String tokenHash);
}
