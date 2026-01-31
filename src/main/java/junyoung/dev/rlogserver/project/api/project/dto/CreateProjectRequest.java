package junyoung.dev.rlogserver.project.api.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest (
	@NotBlank(message = "프로젝트 이름은 필수입니다.")
	@Size(max = 100, message = "프로젝트 이름은 100자를 초과할 수 없습니다.")
	String name,

	@Size(max = 255, message = "설명은 255자를 초과할 수 없습니다.")
	String description,

	@NotBlank(message = "프로젝트 키는 필수입니다.")
	@Size(max = 255, message = "프로젝트 키는 255자를 초과할 수 없습니다.")
	String projectKey,

	@Size(max = 255, message = "타임존은 255자를 초과할 수 없습니다.")
	String timezone
) {

}