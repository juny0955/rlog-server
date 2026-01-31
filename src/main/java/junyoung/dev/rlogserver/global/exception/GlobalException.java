package junyoung.dev.rlogserver.global.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

	private final ErrorCode errorCode;

	public GlobalException(ErrorCode errorCode) {
		super(errorCode.message());
		this.errorCode = errorCode;
	}

	public GlobalException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
}
