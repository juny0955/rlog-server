package junyoung.dev.rlogserver.agent.inbound.api.dto;

import java.time.Instant;
import java.util.List;

import junyoung.dev.rlogserver.proto.log.Log;
import junyoung.dev.rlogserver.proto.log.LogBatch;

public record LiveLogMessage(
	Long agentId,
	String batchId,
	Instant sendAt,
	List<LiveLog> logs
) {
	public static LiveLogMessage from(Long agentId, LogBatch batch) {
		return new LiveLogMessage(
			agentId,
			batch.getBatchId(),
			Instant.ofEpochSecond(batch.getSendAt().getSeconds(), batch.getSendAt().getNanos()),
			batch.getLogsList().stream().map(LiveLog::from).toList()
		);
	}

	public record LiveLog(String label, String line, Instant timestamp) {
		public static LiveLog from(Log log) {
			return new LiveLog(
				log.getLabel(),
				log.getLine(),
				Instant.ofEpochSecond(log.getTimestamp().getSeconds(), log.getTimestamp().getNanos())
			);
		}
	}
}