package com.clubulprogramatorilor.tools.games.tools;

import java.util.Map;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ToolsConfiguration {

	@Tool(name = "getGameRewards",
          description = "Get game reward details by game name with the following options: Monopoly, Chess, Scrabble")
    public String getWebSearchResult(String gameName) {
        var gameDetails = getAllGameRewardDatils();
        var gameInfo = gameDetails.getOrDefault(gameName, "Game not found or no rewards available.");
		log.info("Game reward details for {}: {}", gameName, gameInfo);
        return gameInfo;
    }

    private Map<String, String> getAllGameRewardDatils() {
        return Map.of("Monopoly", "Monopoly game can reward with virtual money, properties, and chance cards. Owner team is Chips'n'Dale.",
                      "Chess", "Chess game can reward with points based on the pieces captured and the final outcome of the game. Owner team is Code Reapears.",
                      "Scrabble", "Scrabble game can reward with points based on the words formed and their letter values. Owner team is Wild Aces.");
    }
}
