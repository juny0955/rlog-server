package junyoung.dev.rlogserver.agent.inbound.api.dto;

import java.time.LocalDateTime;

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
) { }
