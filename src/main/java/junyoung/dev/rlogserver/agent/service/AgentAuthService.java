package junyoung.dev.rlogserver.agent.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import junyoung.dev.rlogserver.agent.exception.AgentErrorCode;
import junyoung.dev.rlogserver.agent.repository.Agent;
import junyoung.dev.rlogserver.agent.repository.AgentRefreshToken;
import junyoung.dev.rlogserver.agent.repository.AgentRepository;
import junyoung.dev.rlogserver.agent.repository.AgentTokenRepository;
import junyoung.dev.rlogserver.global.exception.GlobalException;
import junyoung.dev.rlogserver.global.grpc.AgentJwtTokenProvider;
import junyoung.dev.rlogserver.project.exception.ProjectErrorCode;
import junyoung.dev.rlogserver.project.repository.ProjectRepository;
import junyoung.dev.rlogserver.project.repository.entity.AgentConfig;
import junyoung.dev.rlogserver.project.repository.entity.AgentConfigSource;
import junyoung.dev.rlogserver.project.repository.entity.Project;
import junyoung.dev.rlogserver.proto.auth.RefreshRequest;
import junyoung.dev.rlogserver.proto.auth.RefreshResponse;
import junyoung.dev.rlogserver.proto.auth.RegisterRequest;
import junyoung.dev.rlogserver.proto.auth.RegisterResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AgentAuthService {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	private final AgentRepository agentRepository;
	private final AgentTokenRepository agentTokenRepository;
	private final ProjectRepository projectRepository;
	private final AgentJwtTokenProvider agentJwtTokenProvider;

	@Transactional
	public RegisterResponse register(RegisterRequest request) {
		Project project = projectRepository.findByProjectKey(request.getProjectKey())
			.orElseThrow(() -> new GlobalException(ProjectErrorCode.NOT_FOUND));

		// 중복 등록 확인
		if (agentRepository.existsByProjectIdAndHostname(project.getId(), request.getHostname())) {
			throw new GlobalException(AgentErrorCode.ALREADY_REGISTERED);
		}

		Agent agent = agentRepository.save(Agent.create(project.getId(), request.getHostname(), request.getOs()));

		String accessToken = agentJwtTokenProvider.generateToken(agent.getId());
		String refreshToken = generateRefreshToken();
		String refreshTokenHash = sha256(refreshToken);

		agentTokenRepository.save(AgentRefreshToken.create(agent, refreshTokenHash));

		AgentConfig agentConfig = project.getAgentConfig();
		List<AgentConfigSource> sources = agentConfig.getSources();

		List<RegisterResponse.Source> sourcesList = new ArrayList<>();
		for (AgentConfigSource s : sources) {
			sourcesList.add(RegisterResponse.Source.newBuilder()
				.setLabel(s.getLabel())
				.setPath(s.getPath())
				.setEnabled(s.isEnabled())
				.build());
		}

		return RegisterResponse.newBuilder()
			.setSuccess(true)
			.setAccessToken(accessToken)
			.setRefreshToken(refreshToken)
			.setTimezone(agentConfig.getTimezone())
			.setBatchSize(agentConfig.getBatchSize())
			.setFlushIntervalSec(agentConfig.getFlushIntervalSec())
			.addAllSources(sourcesList)
			.build();
	}

	@Transactional
	public RefreshResponse refresh(RefreshRequest request) {
		String tokenHash = sha256(request.getRefreshToken());
		AgentRefreshToken agentRefreshToken = agentTokenRepository.findByTokenHash(tokenHash)
			.orElseThrow(() -> new GlobalException(AgentErrorCode.REFRESH_TOKEN_NOT_FOUND));

		// 만료 검증
		if (agentRefreshToken.isExpired()) {
			agentRefreshToken.expire();
			throw new GlobalException(AgentErrorCode.REFRESH_TOKEN_EXPIRED);
		}

		// 활성 상태 검증
		if (!agentRefreshToken.isActive()) {
			throw new GlobalException(AgentErrorCode.REFRESH_TOKEN_NOT_FOUND);
		}

		Agent agent = agentRefreshToken.getAgent();
		agent.updateLastSeen();

		String accessToken = agentJwtTokenProvider.generateToken(agent.getId());
		String newRefreshToken = generateRefreshToken();
		String newRefreshTokenHash = sha256(newRefreshToken);

		agentRefreshToken.updateTokenHash(newRefreshTokenHash);

		return RefreshResponse.newBuilder()
			.setSuccess(true)
			.setAccessToken(accessToken)
			.setRefreshToken(newRefreshToken)
			.build();
	}

	private String generateRefreshToken() {
		byte[] bytes = new byte[32];
		SECURE_RANDOM.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private String sha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256 algorithm not found", e);
		}
	}
}
