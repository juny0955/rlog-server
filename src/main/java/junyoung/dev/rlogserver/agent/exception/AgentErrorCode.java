package junyoung.dev.rlogserver.agent.exception;

import org.springframework.http.HttpStatus;

import junyoung.dev.rlogserver.global.exception.http.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AgentErrorCode implements ErrorCode {
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
