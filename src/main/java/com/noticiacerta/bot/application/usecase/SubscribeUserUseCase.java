package com.noticiacerta.bot.application.usecase;

import com.noticiacerta.bot.domain.entity.User;
import com.noticiacerta.bot.domain.repository.UserRepository;

public class SubscribeUserUseCase {

    private final UserRepository userRepository;

    public SubscribeUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(SubscribeUserCommand command) {
        return userRepository.findByTelegramChatId(command.telegramChatId())
                .map(existingUser -> {
                    if (!existingUser.isActive()) {
                        existingUser.activate();
                        return userRepository.save(existingUser);
                    }
                    return existingUser;
                })
                .orElseGet(() -> {
                    User newUser = new User(command.telegramChatId(), command.username());

                    return userRepository.save(newUser);
                });
    }
}