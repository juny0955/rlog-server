package junyoung.dev.rlogserver.agent.service.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.agent.exception.AgentErrorCode;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AccessHistoryResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentDetailResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentSummaryResponse;
import junyoung.dev.rlogserver.agent.repository.AgentQueryRepository;
import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import junyoung.dev.rlogserver.global.pagination.PageRequestParam;
import junyoung.dev.rlogserver.global.pagination.PageResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgentQueryService {

	private final AgentQueryRepository agentQueryRepository;

	public PageResponse<AgentResponse> getAgents(Long projectId, PageRequestParam pageRequest) {
		return agentQueryRepository.findAgentsByProjectId(projectId, pageRequest);
	}

	public AgentDetailResponse getAgent(Long id) {
		return agentQueryRepository.findAgentById(id)
			.orElseThrow(() -> new GlobalException(AgentErrorCode.NOT_FOUND));
	}

	public AgentSummaryResponse getAgentSummary(Long projectId) {
		return agentQueryRepository.getAgentSummary(projectId);
	}

	public List<AccessHistoryResponse> getAccessHistories() {
		return agentQueryRepository.findAccessHistories();
	}
}
