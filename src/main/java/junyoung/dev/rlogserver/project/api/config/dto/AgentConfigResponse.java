package junyoung.dev.rlogserver.project.api.config.dto;

import java.util.List;

public record AgentConfigResponse(
	Long id,
	Long batchSize,
	Long flushIntervalSec,
	List<AgentConfigSourceResponse> sources
) {

	public record AgentConfigSourceResponse(
		Long id,
		String label,
		String path,
		boolean enabled
	) {}
}
