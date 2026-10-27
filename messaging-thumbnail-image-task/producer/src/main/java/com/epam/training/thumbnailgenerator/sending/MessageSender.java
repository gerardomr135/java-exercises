package com.epam.training.thumbnailgenerator.sending;

import com.epam.training.thumbnailgenerator.config.MessagingProperties;
import com.epam.training.thumbnailgenerator.message.ThumbnailCreationRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    private final RabbitTemplate rabbitTemplate;
    private final MessagingProperties messagingProperties;

    public MessageSender(RabbitTemplate rabbitTemplate, MessagingProperties messagingProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagingProperties = messagingProperties;
    }

    public void sendMessage(ThumbnailCreationRequest thumbnailCreationRequest) {
        rabbitTemplate.convertAndSend(
                messagingProperties.getExchangeName(),
                messagingProperties.getRoutingKey(),
                thumbnailCreationRequest);
    }
}
