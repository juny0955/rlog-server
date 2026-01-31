package junyoung.dev.rlogserver.agent.inbound.grpc;

import org.springframework.grpc.server.service.GrpcService;

import io.grpc.stub.StreamObserver;
import junyoung.dev.rlogserver.agent.repository.AccessHistoryRepository;
import junyoung.dev.rlogserver.agent.repository.entity.AgentAccessHistory;
import junyoung.dev.rlogserver.agent.repository.entity.AgentAccessStatus;
import junyoung.dev.rlogserver.agent.service.AgentAuthService;
import junyoung.dev.rlogserver.global.exception.GlobalException;
import junyoung.dev.rlogserver.global.grpc.AgentIpInterceptor;
import junyoung.dev.rlogserver.proto.auth.AuthServiceGrpc;
import junyoung.dev.rlogserver.proto.auth.RefreshRequest;
import junyoung.dev.rlogserver.proto.auth.RefreshResponse;
import junyoung.dev.rlogserver.proto.auth.RegisterRequest;
import junyoung.dev.rlogserver.proto.auth.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class AgentAuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

	private final AgentAuthService agentAuthService;
	private final AccessHistoryRepository accessHistoryRepository;

	@Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		String ip = AgentIpInterceptor.AGENT_IP_KEY.get();
		String hostname = request.getHostname();

		try {
			RegisterResponse response = agentAuthService.register(request, ip);
			saveAccessHistory(ip, hostname, AgentAccessStatus.SUCCESS, null);

			responseObserver.onNext(response);
			responseObserver.onCompleted();
		} catch (GlobalException e) {
			saveAccessHistory(ip, hostname, AgentAccessStatus.FAILED, e.getErrorCode().message());
			throw e;
		}
	}

	@Override
	public void refresh(RefreshRequest request, StreamObserver<RefreshResponse> responseObserver) {
		String ip = AgentIpInterceptor.AGENT_IP_KEY.get();

		try {
			RefreshResponse response = agentAuthService.refresh(request);
			saveAccessHistory(ip, null, AgentAccessStatus.SUCCESS, null);

			responseObserver.onNext(response);
			responseObserver.onCompleted();
		} catch (GlobalException e) {
			saveAccessHistory(ip, null, AgentAccessStatus.FAILED, e.getErrorCode().message());
			throw e;
		}
	}

	private void saveAccessHistory(String ip, String hostname, AgentAccessStatus status, String reason) {
		try {
			accessHistoryRepository.save(AgentAccessHistory.create(ip, hostname, status, reason));
		} catch (Exception e) {
			log.warn("Failed to save access history: ip={}, hostname={}, status={}", ip, hostname, status, e);
		}
	}
}
