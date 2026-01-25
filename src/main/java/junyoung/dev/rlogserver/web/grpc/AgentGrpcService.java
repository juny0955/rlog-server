package junyoung.dev.rlogserver.web.grpc;

import org.springframework.grpc.server.service.GrpcService;

import io.grpc.stub.StreamObserver;
import junyoung.dev.rlogserver.proto.AgentServiceGrpc;
import junyoung.dev.rlogserver.proto.LogBatch;
import junyoung.dev.rlogserver.proto.SendAck;

@GrpcService
public class AgentGrpcService extends AgentServiceGrpc.AgentServiceImplBase {

	@Override
	public void send(LogBatch request, StreamObserver<SendAck> responseObserver) {

		SendAck ack = SendAck.newBuilder()
			.setOk(true)
			.setMessage("accepted")
			.setBatchId(request.getBatchId())
			.setAccepted(request.getLogsCount())
			.build();

		responseObserver.onNext(ack);
		responseObserver.onCompleted();
	}
}
