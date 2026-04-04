package com.noticiacerta.bot.application.usecase;

import com.noticiacerta.bot.domain.entity.User;
import com.noticiacerta.bot.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscribeUserUseCase {

    private final UserRepository userRepository;

    public SubscribeUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(SubscribeUserCommand command) {
        Optional<User> existingUserOptional = userRepository.findByTelegramChatId(command.telegramChatId());

        User user;
        if (existingUserOptional.isPresent()) {
            user = existingUserOptional.get();
            if (!user.isActive()) {
                user.activate();
            }
            if (command.topic() != null && !command.topic().trim().isEmpty()) {
                user.addInterest(command.topic().trim().toLowerCase());
            }
        } else {
            user = new User(command.telegramChatId(), command.username());
            if (command.topic() != null && !command.topic().trim().isEmpty()) {
                user.addInterest(command.topic().trim().toLowerCase());
            }
        }
        return userRepository.save(user);
    }
}