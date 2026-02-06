package junyoung.dev.rlogserver.agent.service.heartbeat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.agent.repository.AgentQueryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HeartBeatScheduler {

	private final HeartBeatCache heartBeatCache;
	private final AgentQueryRepository agentQueryRepository;

	@Value("${heartbeat.offline-timeout-ms}")
	private long offlineTimeoutMs;

	@Transactional
	@Scheduled(fixedRateString = "${heartbeat.batch-interval-ms}")
	public void updateLastSeenAt() {
		List<HeartBeatInfo> updates = heartBeatCache.flush();
		if (updates.isEmpty())
			return;

		agentQueryRepository.batchUpdateHeartBeat(updates);
	}

	@Transactional
	@Scheduled(fixedRateString = "${heartbeat.offline-timeout-ms}")
	public void expireOfflineAgents() {
		LocalDateTime threshold = LocalDateTime.now().minus(offlineTimeoutMs, ChronoUnit.MILLIS);
		agentQueryRepository.markOfflineAgents(threshold);
	}
}
