package junyoung.dev.rlogserver.agent.exception;

import io.grpc.Status;
import junyoung.dev.rlogserver.global.exception.grpc.GrpcErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentGrpcErrorCode implements GrpcErrorCode {

	NOT_FOUND("AGENT-NOT-FOUND", Status.NOT_FOUND, "에이전트를 찾을 수 없습니다."),
	PROJECT_NOT_FOUND("AGENT-PROJECT-NOT-FOUND", Status.NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
	REFRESH_TOKEN_NOT_FOUND("AGENT-REFRESH-TOKEN-NOT-FOUND", Status.UNAUTHENTICATED, "유효하지 않은 리프레시 토큰입니다."),
	REFRESH_TOKEN_EXPIRED("AGENT-REFRESH-TOKEN-EXPIRED", Status.UNAUTHENTICATED, "리프레시 토큰이 만료되었습니다."),

	;

	private final String code;
	private final Status status;
	private final String message;

	@Override
	public String code() {
		return code;
	}

	@Override
	public Status status() {
		return status;
	}

	@Override
	public String message() {
		return message;
	}
}