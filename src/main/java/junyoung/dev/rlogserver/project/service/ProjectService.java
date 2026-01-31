package junyoung.dev.rlogserver.project.service;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.GlobalException;
import junyoung.dev.rlogserver.project.api.project.dto.CreateProjectRequest;
import junyoung.dev.rlogserver.project.api.project.dto.CreateProjectResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectDetailResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectKeyResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectResponse;
import junyoung.dev.rlogserver.project.exception.ProjectErrorCode;
import junyoung.dev.rlogserver.project.repository.ProjectRepository;
import junyoung.dev.rlogserver.project.repository.entity.Project;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

	private final ProjectRepository projectRepository;

	@Transactional
	public CreateProjectResponse createProject(CreateProjectRequest request) {
		if (projectRepository.existsByName(request.name()))
			throw  new GlobalException(ProjectErrorCode.ALREADY_EXISTS);

		String tz = request.timezone();
		if (tz == null || tz.isBlank()) {
			tz = "UTC";
		}

		try {
			ZoneId.of(tz); // validate
		} catch (DateTimeException e) {
			throw new GlobalException(ProjectErrorCode.INVALID_TIMEZONE);
		}

		Project saved = projectRepository.save(Project.create(request.name(), request.description(), request.projectKey(), tz));
		return new CreateProjectResponse(saved.getId());
	}


	public List<ProjectResponse> getProjects() {
		List<Project> projects = projectRepository.findAll();

		return projects.stream()
			.map(project -> new ProjectResponse(
				project.getId(),
				project.getName(),
				project.getDescription(),
				project.getStatus().name(),
				project.getUpdatedAt()
			))
			.toList();
	}

	public ProjectDetailResponse getProject(Long id) {
		Project project = projectRepository.findById(id)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));

		return new ProjectDetailResponse();
	}

	public ProjectKeyResponse getProjectKey(Long id) {
		Project project = projectRepository.findById(id)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));

		return new ProjectKeyResponse(project.getProjectKey());
	}
}
