package junyoung.dev.rlogserver.agent.inbound.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import junyoung.dev.rlogserver.agent.inbound.api.dto.AccessHistoryResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentDetailResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentSummaryResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.UpdateAgentNameRequest;
import junyoung.dev.rlogserver.agent.service.command.AgentCommandService;
import junyoung.dev.rlogserver.agent.service.query.AgentQueryService;
import junyoung.dev.rlogserver.global.pagination.PageRequestParam;
import junyoung.dev.rlogserver.global.pagination.PageResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agents")
public class AgentController {

	private final AgentQueryService agentQueryService;
	private final AgentCommandService agentCommandService;

	@GetMapping
	public ResponseEntity<PageResponse<AgentResponse>> getAgents(
		@RequestParam Long projectId,
		@RequestParam(required = false) Integer page,
		@RequestParam(required = false) Integer size) {
		PageResponse<AgentResponse> response = agentQueryService.getAgents(projectId, PageRequestParam.of(page, size));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AgentDetailResponse> getAgent(@PathVariable Long id) {
		AgentDetailResponse response = agentQueryService.getAgent(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/summary")
	public ResponseEntity<AgentSummaryResponse> getAgentSummary(@RequestParam Long projectId) {
		AgentSummaryResponse response = agentQueryService.getAgentSummary(projectId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/access-histories")
	public ResponseEntity<List<AccessHistoryResponse>> getAccessHistories() {
		List<AccessHistoryResponse> responses = agentQueryService.getAccessHistories();
		return ResponseEntity.ok(responses);
	}

	@PatchMapping("/{id}/name")
	public ResponseEntity<Void> updateAgentName(@PathVariable Long id, @Valid @RequestBody UpdateAgentNameRequest request) {
		agentCommandService.updateAgentName(id, request.name());
		return ResponseEntity.noContent().build();
	}

}
