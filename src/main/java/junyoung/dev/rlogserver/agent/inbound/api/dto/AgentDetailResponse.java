package junyoung.dev.rlogserver.agent.inbound.api.dto;

import java.time.LocalDateTime;

import junyoung.dev.rlogserver.agent.repository.entity.Agent;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;

public record AgentDetailResponse(
	Long id,
	String name,
	int sequence,
	AgentStatus status,
	String hostname,
	String ip,
	String os,
	String osVersion,
	LocalDateTime lastSeenAt,
	LocalDateTime createdAt
) {
	public static AgentDetailResponse from(Agent agent) {
		return new AgentDetailResponse(
			agent.getId(),
			agent.getName(),
			agent.getSequence(),
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