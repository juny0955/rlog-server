package junyoung.dev.rlogserver.project.api.project.dto;

import java.time.LocalDateTime;

public record ProjectResponse(
	Long id,
	String name,
	String description,
	String status,
	LocalDateTime createdAt
) {
}
