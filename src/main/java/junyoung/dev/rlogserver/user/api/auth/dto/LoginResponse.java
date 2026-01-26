package junyoung.dev.rlogserver.user.api.auth.dto;

import java.time.LocalDateTime;

public record LoginResponse (
	String accessToken,
	LocalDateTime lastLoginAt,
	boolean isDefaultPassword
) {
}
