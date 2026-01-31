package junyoung.dev.rlogserver.agent.inbound.api.dto;

import java.time.LocalDateTime;

import junyoung.dev.rlogserver.agent.repository.entity.AgentAccessHistory;
import junyoung.dev.rlogserver.agent.repository.entity.AgentAccessStatus;

public record AccessHistoryResponse(
	Long id,
	String ip,
	String hostname,
	AgentAccessStatus status,
	String reason,
	LocalDateTime accessTime
) {
	public static AccessHistoryResponse from(AgentAccessHistory history) {
		return new AccessHistoryResponse(
			history.getId(),
			history.getIp(),
			history.getHostname(),
			history.getStatus(),
			history.getReason(),
			history.getAccessTime()
		);
	}
}