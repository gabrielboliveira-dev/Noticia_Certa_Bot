package com.noticiacerta.bot.application.usecase.impl;

import com.noticiacerta.bot.application.usecase.RecommendationStrategy;
import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class TopicBasedRecommendationStrategy implements RecommendationStrategy {

    @Override
    public List<Article> recommend(User user, List<Article> allArticles) {
        if (user.getInterestedTopics().isEmpty()) {
            return allArticles;
        }

        return allArticles.stream()
                .filter(article -> isInteresting(article, user.getInterestedTopics()))
                .collect(Collectors.toList());
    }

    private boolean isInteresting(Article article, List<String> topics) {
        return topics.stream().anyMatch(topic ->
                article.getTitle().toLowerCase().contains(topic) ||
                        article.getSource().toLowerCase().contains(topic)
        );
    }
}