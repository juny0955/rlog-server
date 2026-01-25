package junyoung.dev.rlogserver.user.domain;

import java.time.LocalDateTime;

public class User {
	private Long id;
	private String username;
	private String password;
	private UserRole role;
	private LocalDateTime lastLoginAt;
}
