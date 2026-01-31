package junyoung.dev.rlogserver.project.api.config.dto;

public final class AddAgentConfigSource {

	public record AddAgentConfigSourceRequest(
		String label,
		String path
	) {
	}
}
