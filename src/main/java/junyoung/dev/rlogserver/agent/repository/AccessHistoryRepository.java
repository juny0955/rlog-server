package junyoung.dev.rlogserver.agent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import junyoung.dev.rlogserver.agent.repository.entity.AgentAccessHistory;

public interface AccessHistoryRepository extends JpaRepository<AgentAccessHistory, Long> {
	List<AgentAccessHistory> findAllByOrderByAccessTimeDesc();
}
