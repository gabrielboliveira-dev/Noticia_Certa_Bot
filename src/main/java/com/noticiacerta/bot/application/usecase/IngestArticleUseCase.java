package com.noticiacerta.bot.application.usecase;

import com.noticiacerta.bot.application.gateway.NotificationGateway;
import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.repository.ArticleRepository;

import java.time.LocalDateTime;

public class IngestArticleUseCase {

    private final ArticleRepository articleRepository;
    private final NotificationGateway notificationGateway;

    public IngestArticleUseCase(ArticleRepository articleRepository, NotificationGateway notificationGateway) {
        this.articleRepository = articleRepository;
        this.notificationGateway = notificationGateway;
    }

    public void execute(String title, String url, String source, LocalDateTime publishedAt) {
        if (articleRepository.existsByUrl(url)) {
            return;
        }

        Article article = new Article(title, url, source, publishedAt);

        if (article.isFresh()) {
            articleRepository.save(article);
            notificationGateway.notifyBreakingNews(article);
        }
    }
}