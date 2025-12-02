package com.noticiacerta.bot.infrastructure.rss;

import com.noticiacerta.bot.application.gateway.NewsFetcherGateway;
import com.noticiacerta.bot.domain.entity.Article;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class RssNewsFetcher implements NewsFetcherGateway {

    @Override
    public List<Article> fetch(String feedUrl) {
        try {
            URL url = new URL(feedUrl);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            String sourceTitle = feed.getTitle();
            List<Article> articles = new ArrayList<>();

            for (SyndEntry entry : feed.getEntries()) {
                try {
                    Article article = mapToArticle(entry, sourceTitle);
                    articles.add(article);
                } catch (Exception e) {
                    System.err.println("Erro ao processar item: " + e.getMessage());
                }
            }

            return articles;

        } catch (Exception e) {
            System.err.println("Erro ao buscar feed " + feedUrl + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private Article mapToArticle(SyndEntry entry, String source) {
        String title = entry.getTitle();
        String link = entry.getLink();
        Date pubDate = entry.getPublishedDate();

        if (pubDate == null) {
            pubDate = new Date();
        }

        LocalDateTime publishedAt = pubDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return new Article(title, link, source, publishedAt);
    }
}