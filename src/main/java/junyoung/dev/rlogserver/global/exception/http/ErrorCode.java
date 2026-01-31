package junyoung.dev.rlogserver.global.exception.http;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	String code();
	HttpStatus status();
	String message();
}
