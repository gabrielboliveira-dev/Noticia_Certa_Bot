package com.noticiacerta.bot.infrastructure.job;

import com.noticiacerta.bot.application.gateway.NewsFetcherGateway;
import com.noticiacerta.bot.application.usecase.IngestArticleUseCase;
import com.noticiacerta.bot.domain.entity.Article;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsUpdateJob {

    private final NewsFetcherGateway newsFetcher;
    private final IngestArticleUseCase ingestArticleUseCase;

    private final List<String> feeds = List.of(
            "https://g1.globo.com/dynamo/tecnologia/rss2.xml",
            "https://rss.tecmundo.com.br/feed"
    );

    public NewsUpdateJob(NewsFetcherGateway newsFetcher, IngestArticleUseCase ingestArticleUseCase) {
        this.newsFetcher = newsFetcher;
        this.ingestArticleUseCase = ingestArticleUseCase;
    }

    @Scheduled(fixedRate = 600000)
    public void run() {
        System.out.println("🔄 Iniciando busca de notícias...");

        for (String feedUrl : feeds) {
            List<Article> articles = newsFetcher.fetch(feedUrl);

            for (Article article : articles) {
                ingestArticleUseCase.execute(
                        article.getTitle(),
                        article.getUrl(),
                        article.getSource(),
                        article.getPublishedAt()
                );
            }
        }

        System.out.println("✅ Busca finalizada.");
    }
}