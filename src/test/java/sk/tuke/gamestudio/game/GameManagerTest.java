package sk.tuke.gamestudio.game;


import org.junit.jupiter.api.BeforeEach;
import sk.tuke.gamestudio.game.ui.TestUI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameManagerTest {

    private GameManager gameManager;
    private TestUI testUI;

    @BeforeEach
    public void setUp() {
        testUI = new TestUI(null);
        gameManager = new GameManager(testUI);
    }

//    @Test
//    public void testPrepareGameClassicMode() {
//        testUI.passInt(1);
//        testUI.showMainMenu();
//        gameManager.prepareGame("classic");
//        assertNotNull(gameManager.getField());
//        assertEquals(3, gameManager.getLives());
//        assertEquals(GameState.PLAYING, gameManager.getField().getGameState());
//    }
//
//    @Test
//    public void testPrepareGameCustomMode() {
//        gameManager.prepareGame("custom");
//        assertNotNull(gameManager.getField());
//        assertEquals(3, gameManager.getLives());
//        assertEquals(GameState.PLAYING, gameManager.getField().getGameState());
//    }
//
//    @Test
//    public void testCalculateStats() {
//        gameManager.prepareGame("classic");
//        gameManager.calculateStats();
//        assertEquals(0, gameManager.getScore());
//        assertEquals(3, gameManager.getLives());
//        assertEquals(GameState.PLAYING, gameManager.getField().getGameState());
//
//    }
}

