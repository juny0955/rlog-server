package junyoung.dev.rlogserver.agent.exception;

import org.springframework.http.HttpStatus;

import junyoung.dev.rlogserver.global.exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentErrorCode implements ErrorCode {

	NOT_FOUND("AGENT-NOT-FOUND", HttpStatus.NOT_FOUND, "에이전트를 찾을 수 없습니다."),
	REFRESH_TOKEN_NOT_FOUND("AGENT-REFRESH-TOKEN-NOT-FOUND", HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
	REFRESH_TOKEN_EXPIRED("AGENT-REFRESH-TOKEN-EXPIRED", HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),

	;

	private final String code;
	private final HttpStatus status;
	private final String message;

	@Override
	public String code() {
		return code;
	}

	@Override
	public HttpStatus status() {
		return status;
	}

	@Override
	public String message() {
		return message;
	}
}
