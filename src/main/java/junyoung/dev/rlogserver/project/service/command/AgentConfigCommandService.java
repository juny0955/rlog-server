package junyoung.dev.rlogserver.project.service.command;

import static junyoung.dev.rlogserver.project.api.config.dto.AddAgentConfigSource.*;

import java.time.ZoneId;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import junyoung.dev.rlogserver.project.api.config.dto.UpdateAgentConfigRequest;
import junyoung.dev.rlogserver.project.exception.ProjectErrorCode;
import junyoung.dev.rlogserver.project.repository.AgentConfigRepository;
import junyoung.dev.rlogserver.project.repository.entity.AgentConfig;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentConfigCommandService {

	private final AgentConfigRepository agentConfigRepository;

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

	public void addSource(Long id, AddAgentConfigSourceRequest request) {
		AgentConfig config = agentConfigRepository.findById(id)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));

		config.addSource(request.label(), request.path());
	}
}
