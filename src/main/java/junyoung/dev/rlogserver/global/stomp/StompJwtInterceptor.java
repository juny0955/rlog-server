package junyoung.dev.rlogserver.global.stomp;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import junyoung.dev.rlogserver.global.security.CustomUserDetails;
import junyoung.dev.rlogserver.global.security.JwtTokenProvider;
import junyoung.dev.rlogserver.user.repository.UserRole;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StompJwtInterceptor implements ChannelInterceptor {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {
		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if (accessor == null) return message;

		if (StompCommand.CONNECT.equals(accessor.getCommand())) {
			String token = extractToken(accessor);
			CustomUserDetails userDetails = validateAndGetUser(token);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			accessor.setUser(authentication);
		}

		return message;
	}

	private String extractToken(StompHeaderAccessor accessor) {
		List<String> authorization = accessor.getNativeHeader("Authorization");
		String bearer = (authorization == null || authorization.isEmpty()) ? null : authorization.getFirst();

		if (bearer == null || !bearer.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Authorization header is missing or invalid");
		}

		return bearer.substring(7);
	}

	private CustomUserDetails validateAndGetUser(String token) {
		if (!jwtTokenProvider.validateToken(token)) {
			throw new IllegalArgumentException("Invalid or expired JWT token");
		}

		Long userId = jwtTokenProvider.getUserIdFromToken(token);
		String username = jwtTokenProvider.getUsernameFromToken(token);
		UserRole role = jwtTokenProvider.getRoleFromToken(token);

		return new CustomUserDetails(userId, username, role);
	}
}
