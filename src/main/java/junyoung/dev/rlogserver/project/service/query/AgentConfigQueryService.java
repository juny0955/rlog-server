package junyoung.dev.rlogserver.project.service.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import junyoung.dev.rlogserver.project.api.config.dto.AgentConfigResponse;
import junyoung.dev.rlogserver.project.exception.ProjectErrorCode;
import junyoung.dev.rlogserver.project.repository.AgentConfigQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgentConfigQueryService {

	private final AgentConfigQueryRepository agentConfigQueryRepository;

	public AgentConfigResponse getAgentConfig(Long projectId) {
		return agentConfigQueryRepository.findByProjectId(projectId)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));
	}
}
