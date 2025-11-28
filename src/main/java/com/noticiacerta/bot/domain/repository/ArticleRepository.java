package com.noticiacerta.bot.domain.repository;

import com.noticiacerta.bot.domain.entity.Article;
import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository {
    Article save(Article article);
    boolean existsByUrl(String url);
    List<Article> findRecentArticles(LocalDateTime since);
}