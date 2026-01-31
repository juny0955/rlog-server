package junyoung.dev.rlogserver.agent.grpc;

import org.springframework.stereotype.Component;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import junyoung.dev.rlogserver.global.grpc.AgentJwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentJwtInterceptor implements ServerInterceptor {

	private static final Metadata.Key<String> AUTHORIZATION_KEY = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
	private static final String BEARER_PREFIX = "Bearer ";
	public static final Context.Key<Long> AGENT_ID_KEY = Context.key("agentId");
	private final AgentJwtTokenProvider agentJwtTokenProvider;

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
		ServerCall<ReqT, RespT> call,
		Metadata headers,
		ServerCallHandler<ReqT, RespT> next
	) {
		String serviceName = call.getMethodDescriptor().getServiceName();

		// AuthService (Register, Refresh)는 인증 없이 접근 가능
		if ("auth.AuthService".equals(serviceName))
			return next.startCall(call, headers);

		String authHeader = headers.get(AUTHORIZATION_KEY);

		if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
			call.close(Status.UNAUTHENTICATED.withDescription("Missing or invalid Authorization header"), new Metadata());
			return new ServerCall.Listener<>() {};
		}

		String token = authHeader.substring(BEARER_PREFIX.length());

		if (!agentJwtTokenProvider.validateToken(token)) {
			call.close(Status.UNAUTHENTICATED.withDescription("Invalid or expired token"), new Metadata());
			return new ServerCall.Listener<>() {};
		}

		Long agentId = agentJwtTokenProvider.getAgentId(token);
		Context context = Context.current().withValue(AGENT_ID_KEY, agentId);

		return Contexts.interceptCall(context, call, headers, next);
	}
}
