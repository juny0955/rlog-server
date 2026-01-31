package junyoung.dev.rlogserver.agent.inbound.api.dto;

public record AgentSummaryResponse(
	long totalAgents,
	long onlineAgents
) {
}
