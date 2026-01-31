package junyoung.dev.rlogserver.agent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.agent.inbound.api.dto.AccessHistoryResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentSummaryResponse;
import junyoung.dev.rlogserver.agent.repository.AccessHistoryRepository;
import junyoung.dev.rlogserver.agent.repository.AgentRepository;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgentService {

	private final AgentRepository agentRepository;
	private final AccessHistoryRepository accessHistoryRepository;

	public List<AgentResponse> getAgents(Long projectId) {
		return agentRepository.findByProjectId(projectId).stream()
			.map(AgentResponse::from)
			.toList();
	}

	public AgentSummaryResponse getAgentSummary(Long projectId) {
		long totalAgents = agentRepository.countByProjectId(projectId);
		long onlineAgents = agentRepository.countByProjectIdAndStatus(projectId, AgentStatus.ONLINE);
		return new AgentSummaryResponse(totalAgents, onlineAgents);
	}

	public List<AccessHistoryResponse> getAccessHistories() {
		return accessHistoryRepository.findAllByOrderByAccessTimeDesc().stream()
			.map(AccessHistoryResponse::from)
			.toList();
	}
}
