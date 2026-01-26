package junyoung.dev.rlogserver.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.user.api.user.dto.ChangePasswordRequest;
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
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

		if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
			throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
		}

		user.changePassword(passwordEncoder.encode(request.newPassword()));
	}
}
