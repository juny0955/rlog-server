package junyoung.dev.rlogserver.global.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
	String code,
	String message,
	int status,
	List<FieldError> errors
) {
	public static ErrorResponse of(ErrorCode errorCode, String message) {
		return new ErrorResponse(
			errorCode.code(),
			message,
			errorCode.status().value(),
			null
		);
	}

	public static ErrorResponse of(ErrorCode errorCode, String message, List<FieldError> errors) {
		return new ErrorResponse(
			errorCode.code(),
			message,
			errorCode.status().value(),
			errors
		);
	}

	public record FieldError(
		String field,
		String message
	) {
	}
}
