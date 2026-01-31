package junyoung.dev.rlogserver.global.exception.grpc;

import io.grpc.Status;

public interface GrpcErrorCode {
	String code();
	Status status();
	String message();
}
