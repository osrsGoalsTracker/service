package com.osrsGoalTracker.notificationChannel.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.osrsGoalTracker.notificationChannel.dao.NotificationChannelDao;
import com.osrsGoalTracker.notificationChannel.dao.entity.NotificationChannelEntity;
import com.osrsGoalTracker.notificationChannel.model.NotificationChannel;
import com.osrsGoalTracker.notificationChannel.repository.NotificationChannelRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the NotificationChannelRepository interface.
 */
@Slf4j
public class NotificationChannelRepositoryImpl implements NotificationChannelRepository {
    private final NotificationChannelDao notificationChannelDao;

    /**
     * Constructor for NotificationChannelRepositoryImpl.
     * 
     * @param notificationChannelDao The notification channel DAO.
     */
    @Inject
    public NotificationChannelRepositoryImpl(NotificationChannelDao notificationChannelDao) {
        this.notificationChannelDao = notificationChannelDao;
    }

    /**
     * Creates a notification channel for a user.
     * 
     * @param userId The user ID.
     * @param channelType The type of notification channel.
     * @param identifier The identifier of the notification channel.
     * @param isActive Whether the notification channel is active.
     * @return The created notification channel.
     */
    @Override
    public NotificationChannel createNotificationChannel(String userId, String channelType, String identifier,
            boolean isActive) {
        log.info("Creating notification channel for user {} of type {}", userId, channelType);

        NotificationChannelEntity entity = notificationChannelDao.createNotificationChannel(userId,
                NotificationChannelEntity.builder()
                        .channelType(channelType)
                        .identifier(identifier)
                        .isActive(isActive)
                        .build());

        return convertToModel(entity);
    }

    /**
     * Retrieves all notification channels for a user.
     * 
     * @param userId The user ID.
     * @return The list of notification channels.
     */
    @Override
    public List<NotificationChannel> getNotificationChannels(String userId) {
        log.info("Getting notification channels for user {}", userId);

        return notificationChannelDao.getNotificationChannels(userId).stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    /**
     * Converts a NotificationChannelEntity to a NotificationChannel model.
     * 
     * @param entity The notification channel entity.
     * @return The notification channel model.
     */
    private NotificationChannel convertToModel(NotificationChannelEntity entity) {
        return NotificationChannel.builder()
                .userId(entity.getUserId())
                .channelType(entity.getChannelType())
                .identifier(entity.getIdentifier())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}