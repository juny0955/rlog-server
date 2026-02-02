package junyoung.dev.rlogserver.agent.inbound.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAgentNameRequest(
	@NotBlank(message = "에이전트 이름은 필수입니다")
	@Size(max = 150, message = "에이전트 이름은 150자를 초과할 수 없습니다")
	String name
) {
}
