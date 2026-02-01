package junyoung.dev.rlogserver.global.stomp;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import junyoung.dev.rlogserver.agent.inbound.api.dto.LiveLogMessage;
import junyoung.dev.rlogserver.proto.log.LogBatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StompMessageService {

	private static final String TOPIC_LIVE_LOG = "/topic/liveLog/";

	private final SimpMessagingTemplate template;

	public void sendLiveLog(Long agentId, LogBatch batch) {
		LiveLogMessage message = LiveLogMessage.from(agentId, batch);
		template.convertAndSend(TOPIC_LIVE_LOG + agentId, message);
		log.info("Live log sent to agent: {}", agentId);
	}
}
