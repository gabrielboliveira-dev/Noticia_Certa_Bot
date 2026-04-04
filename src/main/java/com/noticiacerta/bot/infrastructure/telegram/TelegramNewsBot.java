package com.noticiacerta.bot.infrastructure.telegram;

import com.noticiacerta.bot.application.usecase.ListArticlesUseCase;
import com.noticiacerta.bot.application.usecase.RecommendArticlesUseCase;
import com.noticiacerta.bot.application.usecase.SubscribeUserCommand;
import com.noticiacerta.bot.application.usecase.SubscribeUserUseCase;
import com.noticiacerta.bot.domain.entity.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramNewsBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final SubscribeUserUseCase subscribeUserUseCase;
    private final ListArticlesUseCase listArticlesUseCase;
    private final RecommendArticlesUseCase recommendArticlesUseCase;

    public TelegramNewsBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            SubscribeUserUseCase subscribeUserUseCase,
            ListArticlesUseCase listArticlesUseCase,
            RecommendArticlesUseCase recommendArticlesUseCase) {
        super(botToken);
        this.botUsername = botUsername;
        this.subscribeUserUseCase = subscribeUserUseCase;
        this.listArticlesUseCase = listArticlesUseCase;
        this.recommendArticlesUseCase = recommendArticlesUseCase;
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
            if (username == null || username.isEmpty()) {
                username = update.getMessage().getFrom().getFirstName();
            }

            if (messageText.equals("/start")) {
                handleStartCommand(chatId, username);
            } else if (messageText.equals("/news")) {
                handleNewsCommand(chatId);
            } else if (messageText.startsWith("/subscribe")) {
                handleSubscribeCommand(chatId, username, messageText);
            } else if (messageText.equals("/recommend")) {
                handleRecommendCommand(chatId);
            }
            else {
                sendMessage(chatId, "Comando não reconhecido. Tente /start, /news, /subscribe <tópico> ou /recommend");
            }
        }
    }

    private void handleStartCommand(Long chatId, String username) {
        try {
            SubscribeUserCommand command = new SubscribeUserCommand(chatId, username, null);
            subscribeUserUseCase.execute(command);

            sendMessage(chatId, "Olá, " + username + "! 👋\nVocê foi inscrito com sucesso no Notícia Certa Bot.\nUse /news para ver as últimas notícias ou /subscribe <tópico> para personalizar seu feed.");

        } catch (Exception e) {
            sendMessage(chatId, "Ocorreu um erro ao te inscrever. Tente novamente mais tarde.");
            e.printStackTrace();
        }
    }

    private void handleSubscribeCommand(Long chatId, String username, String messageText) {
        String topic = null;
        if (messageText.trim().length() > "/subscribe".length()) {
            topic = messageText.substring("/subscribe".length()).trim();
        }

        try {
            SubscribeUserCommand command = new SubscribeUserCommand(chatId, username, topic);
            subscribeUserUseCase.execute(command);

            if (topic != null && !topic.isEmpty()) {
                sendMessage(chatId, "Você foi inscrito no tópico '" + topic + "' com sucesso!");
            } else {
                sendMessage(chatId, "Você já está inscrito. Use /subscribe <tópico> para adicionar um novo tópico de interesse.");
            }
        } catch (Exception e) {
            sendMessage(chatId, "Ocorreu um erro ao processar sua inscrição. Tente novamente mais tarde.");
            e.printStackTrace();
        }
    }

    private void handleNewsCommand(Long chatId) {
        List<Article> articles = listArticlesUseCase.execute();
        if (articles.isEmpty()) {
            sendMessage(chatId, "Desculpe, não há notícias disponíveis no momento.");
            return;
        }

        StringBuilder sb = new StringBuilder("📰 Últimas Notícias:\n\n");
        for (Article article : articles) {
            sb.append("▪️ ").append(article.getTitle()).append("\n");
            sb.append("Fonte: ").append(article.getSource()).append("\n");
            sb.append("Link: ").append(article.getUrl()).append("\n\n");
        }
        sendMessage(chatId, sb.toString());
    }

    private void handleRecommendCommand(Long chatId) {
        try {
            List<Article> recommendedArticles = recommendArticlesUseCase.execute(chatId);
            if (recommendedArticles.isEmpty()) {
                sendMessage(chatId, "Desculpe, não há recomendações para você no momento. Tente ler algumas notícias primeiro ou adicionar tópicos de interesse com /subscribe <tópico>.");
                return;
            }

            StringBuilder sb = new StringBuilder("✨ Recomendações para você:\n\n");
            for (Article article : recommendedArticles) {
                sb.append("▪️ ").append(article.getTitle()).append("\n");
                sb.append("Fonte: ").append(article.getSource()).append("\n");
                sb.append("Link: ").append(article.getUrl()).append("\n\n");
            }
            sendMessage(chatId, sb.toString());
        } catch (IllegalArgumentException e) {
            sendMessage(chatId, e.getMessage() + " Por favor, use /start para se registrar primeiro.");
        } catch (Exception e) {
            sendMessage(chatId, "Ocorreu um erro ao buscar recomendações. Tente novamente mais tarde.");
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