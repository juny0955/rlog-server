package junyoung.dev.rlogserver.global.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.user.domain.UserRole;
import junyoung.dev.rlogserver.user.repository.UserEntity;
import junyoung.dev.rlogserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		UserEntity admin = UserEntity.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.role(UserRole.ADMIN.name())
				.build();

		if (!userRepository.existsByUsername(admin.getUsername())) {
			userRepository.save(admin);
		}
	}
}
