package com.noticiacerta.bot.application.usecase;

import com.noticiacerta.bot.domain.entity.Article;
import com.noticiacerta.bot.domain.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListArticlesUseCase {

    private final ArticleRepository articleRepository;

    public ListArticlesUseCase(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> execute() {

        return articleRepository.findTop5ByOrderByPublishedAtDesc();
    }
}
