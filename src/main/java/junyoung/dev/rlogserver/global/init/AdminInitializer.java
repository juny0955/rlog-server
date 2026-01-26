package junyoung.dev.rlogserver.global.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.user.repository.UserRole;
import junyoung.dev.rlogserver.user.repository.User;
import junyoung.dev.rlogserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

	private static final String DEFAULT_ADMIN_USERNAME = "admin";
	private static final String DEFAULT_ADMIN_PASSWORD = "admin";

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if (!userRepository.existsByUsername(DEFAULT_ADMIN_USERNAME)) {
			User admin = User.builder()
				.username(DEFAULT_ADMIN_USERNAME)
				.password(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD))
				.role(UserRole.ADMIN)
				.isDefaultPassword(true)
				.build();

			userRepository.save(admin);
		}
	}
}
