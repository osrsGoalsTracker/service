package com.osrsGoalTracker.user.repository.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import com.osrsGoalTracker.dao.goalTracker.GoalTrackerDao;
import com.osrsGoalTracker.dao.goalTracker.entity.UserEntity;
import com.osrsGoalTracker.user.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private GoalTrackerDao goalTrackerDao;

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(goalTrackerDao);
    }

    @Test
    void createUser_ValidEmail_ReturnsUser() {
        // Given
        String email = "test@example.com";
        LocalDateTime now = LocalDateTime.now();
        String userId = "user123";

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .build();

        UserEntity createdEntity = UserEntity.builder()
                .userId(userId)
                .email(email)
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(goalTrackerDao.createUser(userEntity)).thenReturn(createdEntity);

        // When
        User result = userRepository.createUser(email);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(email, result.getEmail());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
        verify(goalTrackerDao).createUser(userEntity);
    }

    @Test
    void createUser_NullEmail_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> userRepository.createUser(null));
    }

    @Test
    void createUser_EmptyEmail_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> userRepository.createUser(""));
    }

    @Test
    void createUser_WhitespaceEmail_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> userRepository.createUser("   "));
    }

    @Test
    void getUser_ValidUserId_ReturnsUser() {
        // Given
        String userId = "user123";
        String email = "test@example.com";
        LocalDateTime now = LocalDateTime.now();

        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .email(email)
                .createdAt(now)
                .updatedAt(now)
                .build();

        when(goalTrackerDao.getUser(userId)).thenReturn(userEntity);

        // When
        User result = userRepository.getUser(userId);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals(email, result.getEmail());
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
        verify(goalTrackerDao).getUser(userId);
    }

    @Test
    void getUser_NullUserId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> userRepository.getUser(null));
    }

    @Test
    void getUser_EmptyUserId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> userRepository.getUser(""));
    }

    @Test
    void getUser_WhitespaceUserId_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> userRepository.getUser("   "));
    }
}