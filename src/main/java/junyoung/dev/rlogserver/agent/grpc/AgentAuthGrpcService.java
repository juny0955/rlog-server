package junyoung.dev.rlogserver.agent.grpc;

import org.springframework.grpc.server.service.GrpcService;

import io.grpc.stub.StreamObserver;
import junyoung.dev.rlogserver.agent.service.AgentAuthService;
import junyoung.dev.rlogserver.proto.auth.AuthServiceGrpc;
import junyoung.dev.rlogserver.proto.auth.RefreshRequest;
import junyoung.dev.rlogserver.proto.auth.RefreshResponse;
import junyoung.dev.rlogserver.proto.auth.RegisterRequest;
import junyoung.dev.rlogserver.proto.auth.RegisterResponse;
import lombok.RequiredArgsConstructor;

@GrpcService
@RequiredArgsConstructor
public class AgentAuthGrpcService extends AuthServiceGrpc.AuthServiceImplBase {

	private final AgentAuthService agentAuthService;


	@Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		RegisterResponse response = agentAuthService.register(request);

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void refresh(RefreshRequest request, StreamObserver<RefreshResponse> responseObserver) {
		RefreshResponse response = agentAuthService.refresh(request);

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
