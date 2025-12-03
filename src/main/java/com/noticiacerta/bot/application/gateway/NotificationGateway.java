package com.noticiacerta.bot.application.gateway;

import com.noticiacerta.bot.domain.entity.Article;

public interface NotificationGateway {
    void notifyBreakingNews(Article article);
}