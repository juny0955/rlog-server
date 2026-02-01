package junyoung.dev.rlogserver.agent.inbound.api.dto;

import java.time.LocalDateTime;

import junyoung.dev.rlogserver.agent.repository.entity.Agent;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;

public record AgentResponse(
	Long id,
	String name,
	AgentStatus status,
	String hostname,
	String ip,
	String os,
	String osVersion,
	LocalDateTime lastSeenAt,
	LocalDateTime createdAt
) {
	public static AgentResponse from(Agent agent) {
		return new AgentResponse(
			agent.getId(),
			agent.getName(),
			agent.getStatus(),
			agent.getHostname(),
			agent.getIp(),
			agent.getOs(),
			agent.getOsVersion(),
			agent.getLastSeenAt(),
			agent.getCreatedAt()
		);
	}
}