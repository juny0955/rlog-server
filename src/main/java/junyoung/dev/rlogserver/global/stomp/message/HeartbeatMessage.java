package junyoung.dev.rlogserver.global.stomp.message;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import junyoung.dev.rlogserver.proto.health.HeartbeatRequest;

public record HeartbeatMessage(
	Long agentId,
	LocalDateTime timestamp,
	double cpu,
	double memory
) {
	public static HeartbeatMessage from(Long agentId, HeartbeatRequest request) {
		Instant instant = Instant.ofEpochSecond(
			request.getTimestamp().getSeconds(),
			request.getTimestamp().getNanos()
		);
		return new HeartbeatMessage(
			agentId,
			LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),
			request.getCpu(),
			request.getMemory()
		);
	}
}