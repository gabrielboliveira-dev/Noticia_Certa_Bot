package com.noticiacerta.bot.infrastructure.telegram;

import com.noticiacerta.bot.application.usecase.SubscribeUserCommand;
import com.noticiacerta.bot.application.usecase.SubscribeUserUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramNewsBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final SubscribeUserUseCase subscribeUserUseCase;

    public TelegramNewsBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            SubscribeUserUseCase subscribeUserUseCase) {
        super(botToken);
        this.botUsername = botUsername;
        this.subscribeUserUseCase = subscribeUserUseCase;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName();

            if (messageText.equals("/start")) {
                handleStartCommand(chatId, username);
            } else {
                sendMessage(chatId, "Comando não reconhecido. Tente /start");
            }
        }
    }

    private void handleStartCommand(Long chatId, String username) {
        try {
            SubscribeUserCommand command = new SubscribeUserCommand(chatId, username);
            subscribeUserUseCase.execute(command);

            sendMessage(chatId, "Olá, " + username + "! 👋\nVocê foi inscrito com sucesso no Notícia Certa Bot.\nEm breve você receberá as notícias mais quentes!");

        } catch (Exception e) {
            sendMessage(chatId, "Ocorreu um erro ao te inscrever. Tente novamente mais tarde.");
            e.printStackTrace();
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}