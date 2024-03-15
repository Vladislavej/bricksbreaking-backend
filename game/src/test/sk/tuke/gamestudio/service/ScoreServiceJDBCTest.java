package test.sk.tuke.gamestudio.service;
import org.junit.jupiter.api.*;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
public class ScoreServiceJDBCTest {
    private static final String TEST_GAME = "test_game";
    private ScoreServiceJDBC scoreService;

    @BeforeEach
    void setUp() {
        scoreService = new ScoreServiceJDBC();
        scoreService.reset(); // Reset the scores before each test
    }

    @Test
    void addScoreAndGetTopScores() {
        // Add some scores
        scoreService.addScore(new Score(TEST_GAME, "Player1", 100, new java.util.Date()));
        scoreService.addScore(new Score(TEST_GAME, "Player2", 150, new java.util.Date()));
        scoreService.addScore(new Score(TEST_GAME, "Player3", 200, new java.util.Date()));

        // Retrieve top scores
        List<Score> topScores = scoreService.getTopScores(TEST_GAME);

        // Check if the top scores are retrieved correctly
        assertEquals(3, topScores.size());
        assertEquals("Player3", topScores.get(0).getPlayer()); // Highest score
        assertEquals("Player2", topScores.get(1).getPlayer()); // Second highest score
        assertEquals("Player1", topScores.get(2).getPlayer()); // Third highest score
    }

    @Test
    void resetScores() {
        // Add some scores
        scoreService.addScore(new Score(TEST_GAME, "Player1", 100, new java.util.Date()));
        scoreService.addScore(new Score(TEST_GAME, "Player2", 150, new java.util.Date()));

        // Reset scores
        scoreService.reset();

        // Check if no scores exist after reset
        List<Score> topScores = scoreService.getTopScores(TEST_GAME);
        assertEquals(0, topScores.size());
    }
}
