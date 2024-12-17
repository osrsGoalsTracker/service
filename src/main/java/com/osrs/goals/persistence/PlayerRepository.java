package com.osrs.goals.persistence;

import com.osrs.goals.model.Player;
import java.util.Optional;

public interface PlayerRepository {
    Optional<Player> getPlayer(String rsn);
    void savePlayer(Player player);
} 