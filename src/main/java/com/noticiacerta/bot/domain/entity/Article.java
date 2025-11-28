package com.noticiacerta.bot.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Article {

    private final String title;
    private final String url;
    private final String source;
    private final LocalDateTime publishedAt;

    public Article(String title, String url, String source, LocalDateTime publishedAt) {
        validate(title, url, source, publishedAt);
        this.title = title;
        this.url = url;
        this.source = source;
        this.publishedAt = publishedAt;
    }

    private void validate(String title, String url, String source, LocalDateTime publishedAt) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Article title cannot be empty");
        }
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Article URL cannot be empty");
        }
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Article source cannot be empty");
        }
        if (publishedAt == null) {
            throw new IllegalArgumentException("Publication date cannot be null");
        }
        if (publishedAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Article cannot be from the future");
        }
    }

    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getSource() { return source; }
    public LocalDateTime getPublishedAt() { return publishedAt; }

    public boolean isFresh() {
        return publishedAt.isAfter(LocalDateTime.now().minusHours(1));
    }
}