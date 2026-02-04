package junyoung.dev.rlogserver.agent.service.heartbeat;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HeartBeatScheduler {

	private final HeartBeatCache heartBeatCache;

	@Scheduled(fixedRateString = "${heartbeat.batch-interval-ms}")
	public void updateLastSeenAt() {
		List<HeartBeatInfo> updates = heartBeatCache.flush();
		if (updates.isEmpty())
			return;

	}

	@Scheduled(fixedRateString = "${heartbeat.offline-timeout-ms}")
	public void expireOfflineAgents() {

	}
}
