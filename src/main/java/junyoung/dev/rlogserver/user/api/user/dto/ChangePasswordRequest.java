package junyoung.dev.rlogserver.user.api.user.dto;

public record ChangePasswordRequest(
	String currentPassword,
	String newPassword
) {
}
