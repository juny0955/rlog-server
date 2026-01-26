package junyoung.dev.rlogserver.user.api.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import junyoung.dev.rlogserver.global.security.CustomUserDetails;
import junyoung.dev.rlogserver.user.api.user.dto.ChangePasswordRequest;
import junyoung.dev.rlogserver.user.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping("/password")
	public ResponseEntity<Void> changePassword(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody ChangePasswordRequest request
	) {
		userService.changePassword(userDetails.userId(), request);
		return ResponseEntity.ok().build();
	}
}
