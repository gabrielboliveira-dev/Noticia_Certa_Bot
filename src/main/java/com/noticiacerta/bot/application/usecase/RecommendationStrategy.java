package com.noticiacerta.bot.application.usecase;

import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.entity.User;
import java.util.List;

public interface RecommendationStrategy {
    List<Article> recommend(User user, List<Article> allArticles);
}