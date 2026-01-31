package junyoung.dev.rlogserver.user.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.GlobalException;
import junyoung.dev.rlogserver.user.api.auth.dto.LoginRequest;
import junyoung.dev.rlogserver.user.api.auth.dto.LoginResponse;
import junyoung.dev.rlogserver.global.security.JwtTokenProvider;
import junyoung.dev.rlogserver.user.exception.UserErrorCode;
import junyoung.dev.rlogserver.user.repository.User;
import junyoung.dev.rlogserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public LoginResponse login(LoginRequest request) {
		User user = userRepository.findByUsername(request.username())
			.orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND));

		if (!passwordEncoder.matches(request.password(), user.getPassword()))
			throw new GlobalException(UserErrorCode.PASSWORD_MISMATCH);

		String token = jwtTokenProvider.generateToken(
			user.getId(),
			user.getUsername(),
			user.getRole()
		);

		LocalDateTime lastLoginAt = user.getLastLoginAt();
		user.updateLastLoginAt();

		return new LoginResponse(token, lastLoginAt, user.isDefaultPassword());
	}
}