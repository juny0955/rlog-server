package junyoung.dev.rlogserver.user.exception;

import org.springframework.http.HttpStatus;

import junyoung.dev.rlogserver.global.exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
	NOT_FOUND("USER-NOT-FOUND", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
	PASSWORD_MISMATCH("USER-PASSWORD-MISMATCH", HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.")

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
