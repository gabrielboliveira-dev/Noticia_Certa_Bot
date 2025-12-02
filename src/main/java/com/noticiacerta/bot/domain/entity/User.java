package com.noticiacerta.bot.domain.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class User {

    private final UUID id;
    private final Long telegramChatId;
    private final String username;
    private final List<String> interestedTopics;
    private boolean active;

    public User(Long telegramChatId, String username) {
        if (telegramChatId == null) throw new IllegalArgumentException("Chat ID cannot be null");
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username cannot be empty");

        this.id = UUID.randomUUID();
        this.telegramChatId = telegramChatId;
        this.username = username;
        this.interestedTopics = new ArrayList<>();
        this.active = true;
    }

    public User(UUID id, Long telegramChatId, String username, List<String> interestedTopics, boolean active) {
        this.id = id;
        this.telegramChatId = telegramChatId;
        this.username = username;
        this.interestedTopics = new ArrayList<>(interestedTopics);
        this.active = active;
    }

    public void addInterest(String topic) {
        if (topic != null && !topic.isBlank() && !interestedTopics.contains(topic.toLowerCase())) {
            this.interestedTopics.add(topic.toLowerCase());
        }
    }

    public void removeInterest(String topic) {
        this.interestedTopics.remove(topic.toLowerCase());
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public UUID getId() {
        return id;
    }

    public Long getTelegramChatId() {
        return telegramChatId;
    }

    public String getUsername() {
        return username;
    }
    public boolean isActive() {
        return active;
    }

    public List<String> getInterestedTopics() {
        return Collections.unmodifiableList(interestedTopics);
    }
}