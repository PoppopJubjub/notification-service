package com.popjub.notificationservice.infrastructure.sender;

import java.io.IOException;

import org.springframework.stereotype.Component;
import com.popjub.notificationservice.domain.entity.ChannelType;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackSender implements ChannelSender{
	private final Slack slack = Slack.getInstance();

	@Override
	public boolean supports(ChannelType channel) {
		return channel == ChannelType.SLACK;
	}

	@Override
	public ChannelSendResult send(String webhookUrl, String payload) {
		try {
			WebhookResponse response = slack.send(webhookUrl, payload);

			return new ChannelSendResult(
				true,
				payload,
				String.valueOf(response.getCode()),
				response.getBody(),
				null
			);

		} catch (IOException e) {
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