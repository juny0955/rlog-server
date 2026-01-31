package junyoung.dev.rlogserver.agent.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agents")
public class AgentController {

	@GetMapping
	public ResponseEntity<String> getAgent() {
		return ResponseEntity.ok("Agent");
	}

	@GetMapping("/summary")
	public ResponseEntity<String> getAgentSummary() {
		return ResponseEntity.ok("Agent Summary");
	}


}
