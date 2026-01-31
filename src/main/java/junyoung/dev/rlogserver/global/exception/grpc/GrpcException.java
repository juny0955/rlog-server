package junyoung.dev.rlogserver.global.exception.grpc;

import lombok.Getter;

@Getter
public class GrpcException extends RuntimeException {

	private final GrpcErrorCode errorCode;

	public GrpcException(GrpcErrorCode errorCode) {
		super(errorCode.message());
		this.errorCode = errorCode;
	}

	public GrpcException(GrpcErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
