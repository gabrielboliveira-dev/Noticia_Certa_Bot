package com.noticiacerta.bot.infrastructure.gateway;

import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.repository.ArticleRepository;
import com.noticiacerta.bot.infrastructure.persistence.entity.ArticleJpaEntity;
import com.noticiacerta.bot.infrastructure.persistence.repository.SpringDataArticleRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleRepositoryGateway implements ArticleRepository {

    private final SpringDataArticleRepository springRepository;

    public ArticleRepositoryGateway(SpringDataArticleRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Article save(Article articleDomain) {
        ArticleJpaEntity entity = toJpaEntity(articleDomain);
        ArticleJpaEntity savedEntity = springRepository.save(entity);

        return toDomainEntity(savedEntity);
    }

    @Override
    public boolean existsByUrl(String url) {
        return springRepository.existsByUrl(url);
    }

    @Override
    public List<Article> findRecentArticles(LocalDateTime since) {
        return springRepository.findRecentArticles(since)
                .stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    private ArticleJpaEntity toJpaEntity(Article domain) {
        return new ArticleJpaEntity(
                null,
                domain.getTitle(),
                domain.getUrl(),
                domain.getSource(),
                domain.getPublishedAt(),
                null
        );
    }

    private Article toDomainEntity(ArticleJpaEntity entity) {
        return new Article(
                entity.getTitle(),
                entity.getUrl(),
                entity.getSource(),
                entity.getPublishedAt()
        );
    }
}