package com.noticiacerta.bot.application.usecase;

import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.entity.User;
import com.noticiacerta.bot.domain.repository.ArticleRepository;
import com.noticiacerta.bot.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class RecommendArticlesUseCase {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final RecommendationStrategy strategy;

    public RecommendArticlesUseCase(UserRepository userRepository,
                                    ArticleRepository articleRepository,
                                    RecommendationStrategy strategy) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.strategy = strategy;
    }

    public List<Article> execute(Long telegramChatId) {
        User user = userRepository.findByTelegramChatId(telegramChatId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Article> recentArticles = articleRepository.findRecentArticles(LocalDateTime.now().minusHours(24));

        return strategy.recommend(user, recentArticles);
    }
}