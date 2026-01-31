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
import junyoung.dev.rlogserver.project.api.project.dto.ProjectDetailResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectKeyResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectResponse;
import junyoung.dev.rlogserver.project.service.ProjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

	private final ProjectService projectService;

	@PostMapping
	public ResponseEntity<CreateProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
		CreateProjectResponse response = projectService.createProject(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public ResponseEntity<List<ProjectResponse>> getProjects() {
		List<ProjectResponse> responses = projectService.getProjects();
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjectDetailResponse> getProject(@PathVariable Long id) {
		ProjectDetailResponse response = projectService.getProject(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/project-key")
	public ResponseEntity<ProjectKeyResponse> getProjectKey(@PathVariable Long id) {
		ProjectKeyResponse response = projectService.getProjectKey(id);
		return ResponseEntity.ok(response);
	}
}