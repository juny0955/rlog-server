package junyoung.dev.rlogserver.agent.service.heartbeat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class HeartBeatCache {

	private final ConcurrentHashMap<Long, HeartBeatInfo> cache = new ConcurrentHashMap<>();

	public void put(Long agentId) {
		LocalDateTime time = LocalDateTime.now();

		cache.compute(agentId, (id, existingInfo) -> {
			if (existingInfo == null) {
				return new HeartBeatInfo(id, time);
			} else {
				existingInfo.setLastHeartBeatTime(time);
				return existingInfo;
			}
		});
	}

	public List<HeartBeatInfo> flush() {
		if (cache.isEmpty())
			return Collections.emptyList();

		List<HeartBeatInfo> result = new ArrayList<>();
		Iterator<Map.Entry<Long, HeartBeatInfo>> iterator = cache.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<Long, HeartBeatInfo> entry = iterator.next();
			result.add(entry.getValue());
			iterator.remove();
		}

		return result;
	}
}
