package junyoung.dev.rlogserver.agent.service.heartbeat;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HeartBeatInfo {
	Long agentId;
	LocalDateTime lastHeartBeatTime;

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass())
			return false;
		HeartBeatInfo that = (HeartBeatInfo)o;
		return Objects.equals(agentId, that.agentId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(agentId);
	}
}
