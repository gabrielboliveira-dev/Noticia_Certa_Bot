package com.noticiacerta.bot.application.usecase;

import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.repository.ArticleRepository;

import java.time.LocalDateTime;

public class IngestArticleUseCase {

    private final ArticleRepository articleRepository;

    public IngestArticleUseCase(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void execute(String title, String url, String source, LocalDateTime publishedAt) {

        if (articleRepository.existsByUrl(url)) {
            return;
        }

        Article article = new Article(title, url, source, publishedAt);

        if (article.isFresh()) {
            articleRepository.save(article);
        }
    }
}