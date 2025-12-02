package com.noticiacerta.bot.application.gateway;

import com.noticiacerta.bot.domain.entity.Article;
import java.util.List;

public interface NewsFetcherGateway {

    List<Article> fetch(String feedUrl);
}