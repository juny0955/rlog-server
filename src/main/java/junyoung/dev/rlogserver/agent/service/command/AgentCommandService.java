package junyoung.dev.rlogserver.agent.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.agent.exception.AgentErrorCode;
import junyoung.dev.rlogserver.agent.repository.AgentJpaRepository;
import junyoung.dev.rlogserver.agent.repository.entity.Agent;
import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentCommandService {

	private final AgentJpaRepository agentJpaRepository;

	public void updateAgentName(Long id, String name) {
		Agent agent = agentJpaRepository.findById(id)
			.orElseThrow(() -> new GlobalException(AgentErrorCode.NOT_FOUND));

		if (agentJpaRepository.existsByName(name))
			throw new GlobalException(AgentErrorCode.DUPLICATED_NAME);

		agent.updateName(name);
	}
}
