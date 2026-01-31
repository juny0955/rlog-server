package junyoung.dev.rlogserver.global.grpc;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

@Component
@GlobalServerInterceptor
@Slf4j
public class AgentIpInterceptor implements ServerInterceptor {

	public static final Context.Key<String> AGENT_IP_KEY = Context.key("agentIp");

	@Override
	public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
		ServerCall<ReqT, RespT> serverCall,
		Metadata metadata,
		ServerCallHandler<ReqT, RespT> serverCallHandler
	) {
		String clientIp = extractClientIp(serverCall);

		Context context = Context.current().withValue(AGENT_IP_KEY, clientIp);
		return Contexts.interceptCall(context, serverCall, metadata, serverCallHandler);
	}

	private <ReqT, RespT> String extractClientIp(ServerCall<ReqT, RespT> call) {
		SocketAddress remoteAddr = call.getAttributes()
			.get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);

		if (remoteAddr instanceof InetSocketAddress)
			return ((InetSocketAddress) remoteAddr).getAddress().getHostAddress();

		return null;
	}
}
