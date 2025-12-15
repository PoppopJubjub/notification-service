package com.popjub.notificationservice.infrastructure.sender;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.popjub.notificationservice.domain.entity.ChannelType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DiscordSender implements ChannelSender{
	private final RestTemplate restTemplate;

	@Override
	public boolean supports(ChannelType channel) {
		return channel == ChannelType.DISCORD;
	}

	@Override
	public ChannelSendResult send(String webhookUrl, String payload) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> request = new HttpEntity<>(payload, headers);

			ResponseEntity<String> response =
				restTemplate.exchange(webhookUrl, HttpMethod.POST, request, String.class);

			return new ChannelSendResult(
				true,
				payload,
				String.valueOf(response.getStatusCode().value()),
				response.getBody(),
				null
			);
		} catch (Exception e) {
			return new ChannelSendResult(
				false,
				payload,
				"500",
				null,
				e.getMessage()
			);
		}
	}
}
