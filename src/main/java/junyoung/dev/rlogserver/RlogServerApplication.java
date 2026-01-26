package junyoung.dev.rlogserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RlogServerApplication {

	static void main(String[] args) {
		SpringApplication.run(RlogServerApplication.class, args);
	}

}
