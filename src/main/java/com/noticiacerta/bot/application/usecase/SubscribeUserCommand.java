package com.noticiacerta.bot.application.usecase;

public record SubscribeUserCommand(
        Long telegramChatId,
        String username
) {}