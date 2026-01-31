package junyoung.dev.rlogserver.project.service;

import static junyoung.dev.rlogserver.project.api.config.dto.AddAgentConfigSource.*;
import static junyoung.dev.rlogserver.project.api.config.dto.AgentConfigResponse.*;

import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.GlobalException;
import junyoung.dev.rlogserver.project.api.config.dto.UpdateAgentConfigRequest;
import junyoung.dev.rlogserver.project.api.config.dto.AgentConfigResponse;
import junyoung.dev.rlogserver.project.exception.ProjectErrorCode;
import junyoung.dev.rlogserver.project.repository.AgentConfigRepository;
import junyoung.dev.rlogserver.project.repository.entity.AgentConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgentConfigService {

	private final AgentConfigRepository agentConfigRepository;

	public AgentConfigResponse getAgentConfig(Long projectId) {
		AgentConfig config = agentConfigRepository.findByProjectIdWithSources(projectId)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));

		List<AgentConfigSourceResponse> sources = config.getSources() != null
			? config.getSources().stream()
			.map(source -> new AgentConfigSourceResponse(
				source.getId(),
				source.getLabel(),
				source.getPath(),
				source.isEnabled()
			))
			.toList()
			: List.of();

		return new AgentConfigResponse(
			config.getId(),
			config.getBatchSize(),
			config.getFlushIntervalSec(),
			config.getTimezone(),
			sources
		);
	}

	@Transactional
	public void updateConfig(Long id, UpdateAgentConfigRequest request) {
		AgentConfig config = agentConfigRepository.findById(id)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));

		try {
			ZoneId.of(request.timezone());
		} catch (Exception e) {
			throw new GlobalException(ProjectErrorCode.INVALID_TIMEZONE);
		}

		config.updateConfig(request.batchSize(), request.flushIntervalSec(), request.timezone());
	}

	@Transactional
	public void addSource(Long id, AddAgentConfigSourceRequest request) {
		AgentConfig config = agentConfigRepository.findById(id)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));

		config.addSource(request.label(), request.path());
	}
}
