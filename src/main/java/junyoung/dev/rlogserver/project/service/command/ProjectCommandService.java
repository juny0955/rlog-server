package junyoung.dev.rlogserver.project.service.command;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import junyoung.dev.rlogserver.project.api.project.dto.CreateProjectRequest;
import junyoung.dev.rlogserver.project.api.project.dto.CreateProjectResponse;
import junyoung.dev.rlogserver.project.exception.ProjectErrorCode;
import junyoung.dev.rlogserver.project.repository.ProjectJpaRepository;
import junyoung.dev.rlogserver.project.repository.entity.Project;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectCommandService {

	private final ProjectJpaRepository projectJpaRepository;

	public CreateProjectResponse createProject(CreateProjectRequest request) {
		if (projectJpaRepository.existsByName(request.name()))
			throw new GlobalException(ProjectErrorCode.ALREADY_EXISTS);

		Project saved = projectJpaRepository.save(Project.create(request.name(), request.description(), request.projectKey()));
		return new CreateProjectResponse(saved.getId());
	}
}
