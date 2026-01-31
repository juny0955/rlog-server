package junyoung.dev.rlogserver.project.api.config;

import static junyoung.dev.rlogserver.project.api.config.dto.AddAgentConfigSource.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import junyoung.dev.rlogserver.project.api.config.dto.AgentConfigResponse;
import junyoung.dev.rlogserver.project.api.config.dto.UpdateAgentConfigRequest;
import junyoung.dev.rlogserver.project.service.AgentConfigService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agent-configs")
public class AgentConfigController {

	private final AgentConfigService agentConfigService;

	@GetMapping("/{projectId}")
	public ResponseEntity<AgentConfigResponse> getAgentConfig(@PathVariable Long projectId) {
		return ResponseEntity.ok(agentConfigService.getAgentConfig(projectId));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updateProjectConfig(@PathVariable Long id, @RequestBody UpdateAgentConfigRequest request) {
		agentConfigService.updateConfig(id, request);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{id}/sources")
	public ResponseEntity<Void> addSource(@PathVariable Long id, @RequestBody AddAgentConfigSourceRequest request) {
		agentConfigService.addSource(id, request);
		return ResponseEntity.ok().build();
	}
}
