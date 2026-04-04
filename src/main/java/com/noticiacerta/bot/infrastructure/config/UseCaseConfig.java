package com.noticiacerta.bot.infrastructure.config;

import com.noticiacerta.bot.application.gateway.NotificationGateway;
import com.noticiacerta.bot.application.usecase.IngestArticleUseCase;
import com.noticiacerta.bot.application.usecase.RecommendArticlesUseCase;
import com.noticiacerta.bot.application.usecase.RecommendationStrategy;
import com.noticiacerta.bot.application.usecase.SubscribeUserUseCase;
import com.noticiacerta.bot.application.usecase.impl.TopicBasedRecommendationStrategy;
import com.noticiacerta.bot.domain.repository.ArticleRepository;
import com.noticiacerta.bot.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public SubscribeUserUseCase subscribeUserUseCase(UserRepository userRepository) {
        return new SubscribeUserUseCase(userRepository);
    }

    @Bean
    public IngestArticleUseCase ingestArticleUseCase(ArticleRepository articleRepository, NotificationGateway notificationGateway) {
        return new IngestArticleUseCase(articleRepository, notificationGateway);
    }

    @Bean
    public RecommendationStrategy recommendationStrategy() {
        return new TopicBasedRecommendationStrategy();
    }

    @Bean
    public RecommendArticlesUseCase recommendArticlesUseCase(UserRepository userRepository,
                                                             ArticleRepository articleRepository,
                                                             RecommendationStrategy strategy) {
        return new RecommendArticlesUseCase(userRepository, articleRepository, strategy);
    }
}