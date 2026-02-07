package junyoung.dev.rlogserver.project.api.project;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import junyoung.dev.rlogserver.project.api.project.dto.CreateProjectRequest;
import junyoung.dev.rlogserver.project.api.project.dto.CreateProjectResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectKeyResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectResponse;
import junyoung.dev.rlogserver.project.service.command.ProjectCommandService;
import junyoung.dev.rlogserver.project.service.query.ProjectQueryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

	private final ProjectQueryService projectQueryService;
	private final ProjectCommandService projectCommandService;

	@PostMapping
	public ResponseEntity<CreateProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
		CreateProjectResponse response = projectCommandService.createProject(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ProjectResponse>> getProjects() {
		List<ProjectResponse> responses = projectQueryService.getProjects();
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
		ProjectResponse response = projectQueryService.getProject(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/project-key")
	public ResponseEntity<ProjectKeyResponse> getProjectKey(@PathVariable Long id) {
		ProjectKeyResponse response = projectQueryService.getProjectKey(id);
		return ResponseEntity.ok(response);
	}
}
