package junyoung.dev.rlogserver.global.stomp;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import junyoung.dev.rlogserver.global.stomp.message.HeartbeatMessage;
import junyoung.dev.rlogserver.global.stomp.message.LiveLogMessage;
import junyoung.dev.rlogserver.proto.health.HeartbeatRequest;
import junyoung.dev.rlogserver.proto.log.LogBatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StompMessageService {

	private static final String TOPIC_HEARTBEAT = "/topic/heartbeat/";
	private static final String TOPIC_LIVE_LOG = "/topic/liveLog/";

	private final SimpMessagingTemplate template;

	public void sendLiveLog(Long agentId, LogBatch batch) {
		LiveLogMessage message = LiveLogMessage.from(agentId, batch);
		template.convertAndSend(TOPIC_LIVE_LOG + agentId, message);
		log.debug("Live log sent to agent: {}", agentId);
	}

	public void sendHeartbeat(Long agentId, HeartbeatRequest request) {
		HeartbeatMessage message = HeartbeatMessage.from(agentId, request);
		template.convertAndSend(TOPIC_HEARTBEAT + agentId, message);
		log.debug("Heartbeat sent to agent: {}", agentId);
	}
}
