package com.noticiacerta.bot.infrastructure.persistence.repository;

import com.noticiacerta.bot.infrastructure.persistence.entity.ArticleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SpringDataArticleRepository extends JpaRepository<ArticleJpaEntity, UUID> {

    boolean existsByUrl(String url);

    @Query("SELECT a FROM ArticleJpaEntity a WHERE a.publishedAt >= :sinceDate")
    List<ArticleJpaEntity> findRecentArticles(@Param("sinceDate") LocalDateTime sinceDate);
}