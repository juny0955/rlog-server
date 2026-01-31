package junyoung.dev.rlogserver.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import junyoung.dev.rlogserver.user.api.user.dto.ChangePasswordRequest;
import junyoung.dev.rlogserver.user.exception.UserErrorCode;
import junyoung.dev.rlogserver.user.repository.User;
import junyoung.dev.rlogserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void changePassword(Long userId, ChangePasswordRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND));

		if (!passwordEncoder.matches(request.currentPassword(), user.getPassword()))
			throw new GlobalException(UserErrorCode.PASSWORD_MISMATCH);

		user.changePassword(passwordEncoder.encode(request.newPassword()));
	}
}
