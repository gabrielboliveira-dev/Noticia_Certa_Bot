package com.noticiacerta.bot.infrastructure.config;

import com.noticiacerta.bot.infrastructure.telegram.TelegramNewsBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramNewsBot telegramNewsBot) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramNewsBot);
        System.out.println("🤖 Bot do Telegram registrado e escutando com sucesso!");

        return api;
    }
}