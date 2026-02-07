package junyoung.dev.rlogserver.agent.repository;

import static junyoung.dev.rlogserver.jooq.Tables.*;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import junyoung.dev.rlogserver.agent.inbound.api.dto.AccessHistoryResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentDetailResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentResponse;
import junyoung.dev.rlogserver.agent.inbound.api.dto.AgentSummaryResponse;
import junyoung.dev.rlogserver.agent.repository.entity.AgentAccessStatus;
import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;
import junyoung.dev.rlogserver.global.pagination.PageRequestParam;
import junyoung.dev.rlogserver.global.pagination.PageResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AgentQueryRepository {

	private final DSLContext dsl;

	public PageResponse<AgentResponse> findAgentsByProjectId(Long projectId, PageRequestParam pageRequest) {
		long totalElements = dsl.selectCount()
			.from(AGENTS)
			.where(AGENTS.PROJECT_ID.eq(projectId))
			.fetchOne(0, long.class);

		List<AgentResponse> content = dsl.select(
				AGENTS.ID, AGENTS.NAME, AGENTS.STATUS,
				AGENTS.HOSTNAME, AGENTS.IP, AGENTS.OS, AGENTS.OS_VERSION,
				AGENTS.LAST_SEEN_AT, AGENTS.CREATED_AT)
			.from(AGENTS)
			.where(AGENTS.PROJECT_ID.eq(projectId))
			.orderBy(AGENTS.CREATED_AT.desc())
			.offset(pageRequest.offset())
			.limit(pageRequest.size())
			.fetch(r -> new AgentResponse(
				r.get(AGENTS.ID), r.get(AGENTS.NAME),
				AgentStatus.valueOf(r.get(AGENTS.STATUS)),
				r.get(AGENTS.HOSTNAME), r.get(AGENTS.IP),
				r.get(AGENTS.OS), r.get(AGENTS.OS_VERSION),
				r.get(AGENTS.LAST_SEEN_AT), r.get(AGENTS.CREATED_AT)));

		return PageResponse.of(content, pageRequest.page(), pageRequest.size(), totalElements);
	}

	public Optional<AgentDetailResponse> findAgentById(Long id) {
		return dsl.select(
				AGENTS.ID, AGENTS.NAME, AGENTS.SEQUENCE, AGENTS.STATUS,
				AGENTS.HOSTNAME, AGENTS.IP, AGENTS.OS, AGENTS.OS_VERSION,
				AGENTS.LAST_SEEN_AT, AGENTS.CREATED_AT)
			.from(AGENTS)
			.where(AGENTS.ID.eq(id))
			.fetchOptional(r -> new AgentDetailResponse(
				r.get(AGENTS.ID), r.get(AGENTS.NAME), r.get(AGENTS.SEQUENCE),
				AgentStatus.valueOf(r.get(AGENTS.STATUS)),
				r.get(AGENTS.HOSTNAME), r.get(AGENTS.IP),
				r.get(AGENTS.OS), r.get(AGENTS.OS_VERSION),
				r.get(AGENTS.LAST_SEEN_AT), r.get(AGENTS.CREATED_AT)));
	}

	public AgentSummaryResponse getAgentSummary(Long projectId) {
		var result = dsl.select(
				DSL.count(),
				DSL.count().filterWhere(AGENTS.STATUS.eq(AgentStatus.ONLINE.name())))
			.from(AGENTS)
			.where(AGENTS.PROJECT_ID.eq(projectId))
			.fetchOne();
		return new AgentSummaryResponse(result.value1(), result.value2());
	}

	public List<AccessHistoryResponse> findAccessHistories() {
		return dsl.select(
				AGENT_ACCESS_HISTORY.ID, AGENT_ACCESS_HISTORY.IP,
				AGENT_ACCESS_HISTORY.HOSTNAME, AGENT_ACCESS_HISTORY.STATUS,
				AGENT_ACCESS_HISTORY.REASON, AGENT_ACCESS_HISTORY.ACCESS_TIME)
			.from(AGENT_ACCESS_HISTORY)
			.orderBy(AGENT_ACCESS_HISTORY.ACCESS_TIME.desc())
			.fetch(r -> new AccessHistoryResponse(
				r.get(AGENT_ACCESS_HISTORY.ID), r.get(AGENT_ACCESS_HISTORY.IP),
				r.get(AGENT_ACCESS_HISTORY.HOSTNAME),
				AgentAccessStatus.valueOf(r.get(AGENT_ACCESS_HISTORY.STATUS)),
				r.get(AGENT_ACCESS_HISTORY.REASON),
				r.get(AGENT_ACCESS_HISTORY.ACCESS_TIME)));
	}
}
