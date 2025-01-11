package com.osrsGoalTracker.notificationChannel.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.osrsGoalTracker.notificationChannel.dao.NotificationChannelDao;
import com.osrsGoalTracker.notificationChannel.dao.internal.ddb.impl.DynamoNotificationChannelDao;
import com.osrsGoalTracker.notificationChannel.repository.NotificationChannelRepository;
import com.osrsGoalTracker.notificationChannel.repository.impl.NotificationChannelRepositoryImpl;
import com.osrsGoalTracker.notificationChannel.service.NotificationChannelService;
import com.osrsGoalTracker.notificationChannel.service.impl.NotificationChannelServiceImpl;
import com.osrsGoalTracker.shared.di.SharedDynamoDbModule;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Guice module for notification channel dependencies.
 */
public class NotificationChannelModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SharedDynamoDbModule());
        bind(NotificationChannelService.class).to(NotificationChannelServiceImpl.class);
        bind(NotificationChannelRepository.class).to(NotificationChannelRepositoryImpl.class);
    }

    @Provides
    @Singleton
    NotificationChannelDao provideNotificationChannelDao(DynamoDbClient dynamoDbClient) {
        return new DynamoNotificationChannelDao(dynamoDbClient, System.getenv("NOTIFICATION_CHANNEL_TABLE_NAME"));
    }
}
