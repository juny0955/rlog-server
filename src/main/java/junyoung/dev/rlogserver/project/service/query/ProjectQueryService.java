package junyoung.dev.rlogserver.project.service.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.global.exception.http.GlobalException;
import junyoung.dev.rlogserver.global.pagination.PageRequestParam;
import junyoung.dev.rlogserver.global.pagination.PageResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectKeyResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectResponse;
import junyoung.dev.rlogserver.project.exception.ProjectErrorCode;
import junyoung.dev.rlogserver.project.repository.ProjectQueryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectQueryService {

	private final ProjectQueryRepository projectQueryRepository;

	public PageResponse<ProjectResponse> getProjects(PageRequestParam pageRequest) {
		return projectQueryRepository.findAllProjects(pageRequest);
	}

	public ProjectResponse getProject(Long id) {
		return projectQueryRepository.findProjectById(id)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));
	}

	public ProjectKeyResponse getProjectKey(Long id) {
		return projectQueryRepository.findProjectKeyById(id)
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));
	}
}
