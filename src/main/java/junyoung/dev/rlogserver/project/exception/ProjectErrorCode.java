package junyoung.dev.rlogserver.project.exception;

import org.springframework.http.HttpStatus;

import junyoung.dev.rlogserver.global.exception.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProjectErrorCode implements ErrorCode {
	NOT_FOUND("PRJ-NOT-FOUND", HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다."),
	ALREADY_EXISTS("PRJ-ALREADY-EXISTS", HttpStatus.BAD_REQUEST, "이미 존재하는 프로젝트 이름입니다."),
	INVALID_TIMEZONE("PRJ-INVALID-TZ", HttpStatus.BAD_REQUEST, "유효하지 않은 타임존입니다.");

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
