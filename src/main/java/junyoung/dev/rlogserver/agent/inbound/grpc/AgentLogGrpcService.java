package junyoung.dev.rlogserver.agent.inbound.grpc;

import org.springframework.grpc.server.service.GrpcService;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import junyoung.dev.rlogserver.global.grpc.AgentJwtInterceptor;
import junyoung.dev.rlogserver.global.stomp.StompMessageService;
import junyoung.dev.rlogserver.proto.log.LogBatch;
import junyoung.dev.rlogserver.proto.log.LogServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AgentLogGrpcService extends LogServiceGrpc.LogServiceImplBase {

	private final StompMessageService stompMessageService;

	@Override
	public StreamObserver<LogBatch> send(StreamObserver<Empty> responseObserver) {
		Long agentId = AgentJwtInterceptor.AGENT_ID_KEY.get();

		return new StreamObserver<>() {
			@Override
			public void onNext(LogBatch batch) {
				log.debug("{} 로그 수신", agentId);
				stompMessageService.sendLiveLog(agentId, batch);
			}

			@Override
			public void onError(Throwable t) {
				log.error("로그 수신 중 오류 발생", t);
			}

			@Override
			public void onCompleted() {
				responseObserver.onNext(Empty.getDefaultInstance());
				responseObserver.onCompleted();
			}
		};
	}
}
