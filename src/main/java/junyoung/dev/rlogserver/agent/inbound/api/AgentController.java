package junyoung.dev.rlogserver.agent.inbound.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import junyoung.dev.rlogserver.agent.inbound.api.dto.AccessHistoryResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentSummaryResponse;
import junyoung.dev.rlogserver.agent.service.AgentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agents")
public class AgentController {

	private final AgentService agentService;

	@GetMapping
	public ResponseEntity<List<AgentResponse>> getAgents(@RequestParam Long projectId) {
		List<AgentResponse> responses = agentService.getAgents(projectId);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AgentResponse> getAgent(@PathVariable Long id) {
		AgentResponse response = agentService.getAgent(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/summary")
	public ResponseEntity<AgentSummaryResponse> getAgentSummary(@RequestParam Long projectId) {
		AgentSummaryResponse response = agentService.getAgentSummary(projectId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/access-histories")
	public ResponseEntity<List<AccessHistoryResponse>> getAccessHistories() {
		List<AccessHistoryResponse> responses = agentService.getAccessHistories();
		return ResponseEntity.ok(responses);
	}

}
