package junyoung.dev.rlogserver.agent.grpc;

import org.springframework.grpc.server.service.GrpcService;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import junyoung.dev.rlogserver.proto.log.LogBatch;
import junyoung.dev.rlogserver.proto.log.LogServiceGrpc;
import lombok.extern.slf4j.Slf4j;

@GrpcService
@Slf4j
public class AgentLogGrpcService extends LogServiceGrpc.LogServiceImplBase {

	@Override
	public StreamObserver<LogBatch> send(StreamObserver<Empty> responseObserver) {
		LogBatch batch = LogBatch.getDefaultInstance();
		log.info("로그 수신 완료 {}", batch.toString());
		return super.send(responseObserver);
	}
}
