package com.noticiacerta.bot.infrastructure.gateway;

import com.noticiacerta.bot.domain.entity.User;
import com.noticiacerta.bot.domain.repository.UserRepository;
import com.noticiacerta.bot.infrastructure.persistence.entity.UserJpaEntity;
import com.noticiacerta.bot.infrastructure.persistence.repository.SpringDataUserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryGateway implements UserRepository {

    private final SpringDataUserRepository springRepository;

    public UserRepositoryGateway(SpringDataUserRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public User save(User userDomain) {
        UserJpaEntity entity = toJpaEntity(userDomain);
        UserJpaEntity savedEntity = springRepository.save(entity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<User> findByTelegramChatId(Long telegramChatId) {
        return springRepository.findByTelegramChatId(telegramChatId)
                .map(this::toDomainEntity);
    }

    @Override
    public List<User> findAllActiveUsers() {
        return springRepository.findByActiveTrue()
                .stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    private UserJpaEntity toJpaEntity(User domain) {
        return new UserJpaEntity(
                domain.getId(),
                domain.getTelegramChatId(),
                domain.getUsername(),
                domain.isActive(),
                domain.getInterestedTopics()
        );
    }

    private User toDomainEntity(UserJpaEntity entity) {

        return new User(
                entity.getId(),
                entity.getTelegramChatId(),
                entity.getUsername(),
                entity.getInterestedTopics(),
                entity.isActive()
        );
    }
}