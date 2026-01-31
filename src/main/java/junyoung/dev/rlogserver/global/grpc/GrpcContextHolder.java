package junyoung.dev.rlogserver.global.grpc;

import junyoung.dev.rlogserver.agent.grpc.AgentJwtInterceptor;

public final class GrpcContextHolder {

	private GrpcContextHolder() {
	}

	public static Long getAgentId() {
		return AgentJwtInterceptor.AGENT_ID_KEY.get();
	}

	public static Long requireAgentId() {
		Long agentId = getAgentId();
		if (agentId == null) {
			throw new IllegalStateException("Agent ID not found in gRPC context");
		}
		return agentId;
	}
}