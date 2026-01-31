package junyoung.dev.rlogserver.project.api.config.dto;

public record UpdateAgentConfigRequest(
	Long batchSize,
	Long flushIntervalSec
) {
}
