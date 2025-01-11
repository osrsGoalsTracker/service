package com.osrsGoalTracker.notificationChannel.di;

import com.google.inject.AbstractModule;
import com.osrsGoalTracker.notificationChannel.repository.NotificationChannelRepository;
import com.osrsGoalTracker.notificationChannel.repository.impl.NotificationChannelRepositoryImpl;
import com.osrsGoalTracker.notificationChannel.service.NotificationChannelService;
import com.osrsGoalTracker.notificationChannel.service.impl.NotificationChannelServiceImpl;

/**
 * Guice module for notification channel dependencies.
 */
public class NotificationChannelModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(NotificationChannelService.class).to(NotificationChannelServiceImpl.class);
        bind(NotificationChannelRepository.class).to(NotificationChannelRepositoryImpl.class);
    }
}