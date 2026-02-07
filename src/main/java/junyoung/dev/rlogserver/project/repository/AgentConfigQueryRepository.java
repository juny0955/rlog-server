package junyoung.dev.rlogserver.project.repository;

import static junyoung.dev.rlogserver.jooq.Tables.*;
import static junyoung.dev.rlogserver.project.api.config.dto.AgentConfigResponse.*;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import junyoung.dev.rlogserver.project.api.config.dto.AgentConfigResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AgentConfigQueryRepository {

	private final DSLContext dsl;

	public Optional<AgentConfigResponse> findByProjectId(Long projectId) {
		return dsl.select(
				AGENT_CONFIG.ID, AGENT_CONFIG.BATCH_SIZE,
				AGENT_CONFIG.FLUSH_INTERVAL_SEC, AGENT_CONFIG.TIMEZONE)
			.from(AGENT_CONFIG)
			.where(AGENT_CONFIG.PROJECT_ID.eq(projectId))
			.fetchOptional()
			.map(config -> {
				List<AgentConfigSourceResponse> sources = dsl.select(
						AGENT_CONFIG_SOURCES.ID, AGENT_CONFIG_SOURCES.LABEL,
						AGENT_CONFIG_SOURCES.PATH, AGENT_CONFIG_SOURCES.ENABLED)
					.from(AGENT_CONFIG_SOURCES)
					.where(AGENT_CONFIG_SOURCES.AGENT_CONFIG_ID.eq(config.get(AGENT_CONFIG.ID)))
					.fetch(r -> new AgentConfigSourceResponse(
						r.get(AGENT_CONFIG_SOURCES.ID), r.get(AGENT_CONFIG_SOURCES.LABEL),
						r.get(AGENT_CONFIG_SOURCES.PATH), r.get(AGENT_CONFIG_SOURCES.ENABLED)));

				return new AgentConfigResponse(
					config.get(AGENT_CONFIG.ID),
					config.get(AGENT_CONFIG.BATCH_SIZE),
					config.get(AGENT_CONFIG.FLUSH_INTERVAL_SEC),
					config.get(AGENT_CONFIG.TIMEZONE),
					sources);
			});
	}
}
