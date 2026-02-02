package junyoung.dev.rlogserver.agent.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.agent.exception.AgentErrorCode;
import junyoung.dev.rlogserver.agent.exception.AgentGrpcErrorCode;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AccessHistoryResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentDetailResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentSummaryResponse;
import junyoung.dev.rlogserver.agent.repository.AccessHistoryRepository;
import junyoung.dev.rlogserver.agent.repository.AgentRepository;
import junyoung.dev.rlogserver.agent.repository.entity.Agent;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;
import junyoung.dev.rlogserver.global.exception.grpc.GrpcException;
import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AgentService {

	private final AgentRepository agentRepository;
	private final AccessHistoryRepository accessHistoryRepository;

	public List<AgentResponse> getAgents(Long projectId) {
		return agentRepository.findByProjectId(projectId).stream()
			.map(AgentResponse::from)
			.toList();
	}

	public AgentDetailResponse getAgent(Long id) {
		Agent agent = agentRepository.findById(id)
			.orElseThrow(() -> new GlobalException(AgentErrorCode.NOT_FOUND));

		return AgentDetailResponse.from(agent);
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

	@Transactional
	public void updateAgentName(Long id, String name) {
		Agent agent = agentRepository.findById(id)
			.orElseThrow(() -> new GlobalException(AgentErrorCode.NOT_FOUND));

		if (agentRepository.existsByName(name))
			throw new GlobalException(AgentErrorCode.DUPLICATED_NAME);

		agent.updateName(name);
	}

	@Transactional
	public void processHeartbeat(Long agentId) {
		Agent agent = agentRepository.findById(agentId)
			.orElseThrow(() -> new GrpcException(AgentGrpcErrorCode.NOT_FOUND));

		if (agent.getStatus() != AgentStatus.ONLINE) {
			agent.markOnline();
		} else {
			agent.updateLastSeen();
		}
	}
}
