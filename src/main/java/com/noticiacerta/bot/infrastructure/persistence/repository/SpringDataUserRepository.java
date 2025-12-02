package com.noticiacerta.bot.infrastructure.persistence.repository;

import com.noticiacerta.bot.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {

    Optional<UserJpaEntity> findByTelegramChatId(Long telegramChatId);

    List<UserJpaEntity> findByActiveTrue();
}