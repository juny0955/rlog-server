package junyoung.dev.rlogserver.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommonErrorCode implements ErrorCode {
	VALIDATION_ERROR("COMMON-VALIDATION-ERROR", HttpStatus.BAD_REQUEST, "요청 값이 유효하지 않습니다."),
	INTERNAL_SERVER_ERROR("COMMON-500", HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

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
