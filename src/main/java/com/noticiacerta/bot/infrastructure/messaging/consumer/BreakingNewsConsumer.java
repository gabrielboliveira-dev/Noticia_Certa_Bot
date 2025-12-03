package com.noticiacerta.bot.infrastructure.messaging.consumer;

import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.entity.User;
import com.noticiacerta.bot.domain.repository.UserRepository;
import com.noticiacerta.bot.infrastructure.messaging.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.List;

@Component
public class BreakingNewsConsumer {

    private final UserRepository userRepository;
    private final org.telegram.telegrambots.meta.generics.TelegramBot telegramBot;

    public BreakingNewsConsumer(UserRepository userRepository,
                                org.telegram.telegrambots.meta.generics.TelegramBot telegramBot) {
        this.userRepository = userRepository;
        this.telegramBot = telegramBot;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_BREAKING_NEWS)
    public void handleBreakingNews(Article article) {
        System.out.println("📥 Recebida notícia da fila: " + article.getTitle());
        List<User> users = userRepository.findAllActiveUsers();
        for (User user : users) {
            sendTelegramMessage(user.getTelegramChatId(), article);
        }
    }

    private void sendTelegramMessage(Long chatId, Article article) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("🚨 *BREAKING NEWS* 🚨\n\n" +
                article.getTitle() + "\n\n" +
                "Leia mais: " + article.getUrl());
        message.setParseMode("Markdown");

        try {
            ((TelegramLongPollingBot) telegramBot).execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Falha ao enviar para " + chatId);
        }
    }
}