package junyoung.dev.rlogserver.agent.repository;

import static junyoung.dev.rlogserver.jooq.Tables.*;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import junyoung.dev.rlogserver.agent.repository.entity.AgentStatus;
import junyoung.dev.rlogserver.agent.service.heartbeat.HeartBeatInfo;
import junyoung.dev.rlogserver.jooq.tables.records.AgentsRecord;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AgentCommandRepository {

	private final DSLContext dsl;

	public void batchUpdateHeartBeat(List<HeartBeatInfo> updates) {
		List<AgentsRecord> agentsRecords = updates.stream()
			.map(u -> {
				AgentsRecord record = new AgentsRecord();
				record.setId(u.getAgentId());
				record.setStatus(AgentStatus.ONLINE.name());
				record.setLastSeenAt(u.getLastHeartBeatTime());
				return record;
			})
			.toList();

		dsl.batchUpdate(agentsRecords).execute();
	}

	public void markOfflineAgents(LocalDateTime threshold) {
		dsl.update(AGENTS)
			.set(AGENTS.STATUS, AgentStatus.OFFLINE.name())
			.where(AGENTS.STATUS.eq(AgentStatus.ONLINE.name()))
			.and(AGENTS.LAST_SEEN_AT.isNotNull())
			.and(AGENTS.LAST_SEEN_AT.lessThan(threshold))
			.execute();
	}
}
