package junyoung.dev.rlogserver.global.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.server.GlobalServerInterceptor;

import junyoung.dev.rlogserver.agent.grpc.AgentJwtInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class GrpcConfig {

	private final AgentJwtInterceptor agentJwtInterceptor;

	@Bean
	@GlobalServerInterceptor
	public AgentJwtInterceptor globalAgentJwtInterceptor() {
		return agentJwtInterceptor;
	}
}