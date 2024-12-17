package com.osrs.goals.persistence;

import com.osrs.goals.model.Player;

import lombok.extern.log4j.Log4j2;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
@Log4j2
public class DynamoDbPlayerRepository implements PlayerRepository {
    private static final String TABLE_NAME = "Player";
    private static final String RSN_KEY = "rsn";
    private static final String LAST_UPDATED_KEY = "lastUpdated";

    private final DynamoDbClient dynamoDbClient;

    @Inject
    public DynamoDbPlayerRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public Optional<Player> getPlayer(String rsn) {
        try {
            GetItemResponse response = dynamoDbClient.getItem(GetItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(Map.of(RSN_KEY, AttributeValue.builder().s(rsn).build()))
                    .build());

            if (!response.hasItem()) {
                return Optional.empty();
            }

            return Optional.of(Player.builder()
                    .rsn(response.item().get(RSN_KEY).s())
                    .lastUpdated(response.item().get(LAST_UPDATED_KEY).s())
                    .build());
        } catch (DynamoDbException e) {
            log.info("Error getting player from DynamoDB", e);
            throw new RuntimeException("Error getting player from DynamoDB", e);
        }
    }

    @Override
    public void savePlayer(Player player) {
        try {
            Map<String, AttributeValue> item = new HashMap<>();
            item.put(RSN_KEY, AttributeValue.builder().s(player.getRsn()).build());
            item.put(LAST_UPDATED_KEY, AttributeValue.builder().s(player.getLastUpdated()).build());

            dynamoDbClient.putItem(PutItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .item(item)
                    .build());
        } catch (DynamoDbException e) {
            throw new RuntimeException("Error saving player to DynamoDB", e);
        }
    }
} 