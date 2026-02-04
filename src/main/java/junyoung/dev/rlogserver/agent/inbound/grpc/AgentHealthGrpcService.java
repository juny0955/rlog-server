package junyoung.dev.rlogserver.agent.inbound.grpc;

import org.springframework.grpc.server.service.GrpcService;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import junyoung.dev.rlogserver.agent.service.heartbeat.HeartBeatCache;
import junyoung.dev.rlogserver.global.grpc.AgentJwtInterceptor;
import junyoung.dev.rlogserver.global.stomp.StompMessageService;
import junyoung.dev.rlogserver.proto.health.HealthServiceGrpc;
import junyoung.dev.rlogserver.proto.health.HeartbeatRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AgentHealthGrpcService extends HealthServiceGrpc.HealthServiceImplBase {

	private final HeartBeatCache heartBeatCache;
	private final StompMessageService stompMessageService;

	@Override
	public void heartbeat(HeartbeatRequest request, StreamObserver<Empty> responseObserver) {
		Long agentId = AgentJwtInterceptor.AGENT_ID_KEY.get();

		stompMessageService.sendHeartbeat(agentId, request);
		heartBeatCache.put(agentId);

		responseObserver.onNext(Empty.getDefaultInstance());
		responseObserver.onCompleted();
	}
}
