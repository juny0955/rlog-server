package junyoung.dev.rlogserver.project.repository;

import static junyoung.dev.rlogserver.jooq.Tables.*;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import junyoung.dev.rlogserver.global.pagination.PageRequestParam;
import junyoung.dev.rlogserver.global.pagination.PageResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectKeyResponse;
import junyoung.dev.rlogserver.project.api.project.dto.ProjectResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProjectQueryRepository {

	private final DSLContext dsl;

	public PageResponse<ProjectResponse> findAllProjects(PageRequestParam pageRequest) {
		long totalElements = dsl.selectCount()
			.from(PROJECTS)
			.fetchOne(0, long.class);

		List<ProjectResponse> content = dsl.select(
				PROJECTS.ID, PROJECTS.NAME, PROJECTS.DESCRIPTION,
				PROJECTS.STATUS, PROJECTS.CREATED_AT)
			.from(PROJECTS)
			.orderBy(PROJECTS.CREATED_AT.desc())
			.offset(pageRequest.offset())
			.limit(pageRequest.size())
			.fetch(r -> new ProjectResponse(
				r.get(PROJECTS.ID), r.get(PROJECTS.NAME),
				r.get(PROJECTS.DESCRIPTION), r.get(PROJECTS.STATUS),
				r.get(PROJECTS.CREATED_AT)));

		return PageResponse.of(content, pageRequest.page(), pageRequest.size(), totalElements);
	}

	public Optional<ProjectResponse> findProjectById(Long id) {
		return dsl.select(
				PROJECTS.ID, PROJECTS.NAME, PROJECTS.DESCRIPTION,
				PROJECTS.STATUS, PROJECTS.CREATED_AT)
			.from(PROJECTS)
			.where(PROJECTS.ID.eq(id))
			.fetchOptional(r -> new ProjectResponse(
				r.get(PROJECTS.ID), r.get(PROJECTS.NAME),
				r.get(PROJECTS.DESCRIPTION), r.get(PROJECTS.STATUS),
				r.get(PROJECTS.CREATED_AT)));
	}

	public Optional<ProjectKeyResponse> findProjectKeyById(Long id) {
		return dsl.select(PROJECTS.PROJECT_KEY)
			.from(PROJECTS)
			.where(PROJECTS.ID.eq(id))
			.fetchOptional(r -> new ProjectKeyResponse(r.get(PROJECTS.PROJECT_KEY)));
	}
}
