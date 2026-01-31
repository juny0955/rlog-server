package junyoung.dev.rlogserver.global.exception.grpc;

import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusException;

@Component
public class GlobalGrpcExceptionHandler implements GrpcExceptionHandler {

	private static final Metadata.Key<String> ERROR_CODE_KEY = Metadata.Key.of("error-code", Metadata.ASCII_STRING_MARSHALLER);

	@Override
	public StatusException handleException(Throwable exception) {
		if (exception instanceof GrpcException e) {
			GrpcErrorCode errorCode = e.getErrorCode();

			String description = String.format("[%s] %s", errorCode.code(), e.getMessage());

			Metadata metadata = new Metadata();
			metadata.put(ERROR_CODE_KEY, errorCode.code());

			return errorCode.status().withDescription(description).asException(metadata);
		}

		return Status.INTERNAL.withDescription("서버 오류가 발생했습니다.").asException();
	}
}