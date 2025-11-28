package com.noticiacerta.bot.domain.repository;

import com.noticiacerta.bot.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByTelegramChatId(Long telegramChatId);
    List<User> findAllActiveUsers();
}