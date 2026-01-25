package junyoung.dev.rlogserver.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) {
		return http
			.csrf(csrf -> csrf
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			)
			.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated()
			)
			.formLogin(form -> form
				.loginProcessingUrl("/login")
				.successHandler((req, res, auth) -> res.setStatus(200))
				.failureHandler((req, res, ex) -> res.setStatus(401))
			)
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
			)
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
