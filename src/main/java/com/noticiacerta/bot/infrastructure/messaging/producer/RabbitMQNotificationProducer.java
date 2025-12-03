package com.noticiacerta.bot.infrastructure.messaging.producer;

import com.noticiacerta.bot.application.gateway.NotificationGateway;
import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.infrastructure.messaging.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQNotificationProducer implements NotificationGateway {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQNotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void notifyBreakingNews(Article article) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_BREAKING_NEWS, article);
        System.out.println("📤 Notícia enviada para a fila: " + article.getTitle());
    }
}