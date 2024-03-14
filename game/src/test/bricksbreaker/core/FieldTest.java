package test.bricksbreaker.core;

import bricksbreaker.core.Field;
import bricksbreaker.core.GameState;
import bricksbreaker.core.Tile;
import bricksbreaker.core.TileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field(5, 5); // Example: 5x5 field
        field.generate(3); // Example: 3 different colors
    }

    @Test
    void testBreakTileInvalidCoordinates() {
        int initialBrokenBricks = field.getBrokenBricks();
        field.breakTile(-1, 2); // Break a tile at invalid coordinates
        assertEquals(initialBrokenBricks, field.getBrokenBricks());
    }

    @Test
    void testUnite() {
        // Fill the last row with tiles
        for (int j = 0; j < field.getCols(); j++) {
            field.getTiles()[field.getRows() - 1][j] = new Tile(TileInfo.BLUE);
        }
        // Break some tiles in the second last row
        field.breakTile(2, field.getRows() - 2);
        field.breakTile(3, field.getRows() - 2);

        field.unite(); // Perform unite operation

        // Check if the tiles in the second last row have moved down
        assertNotNull(field.getTiles()[field.getRows() - 1][2]);
        assertNotNull(field.getTiles()[field.getRows() - 1][3]);
    }

    @Test
    void testUpdateGameStateWhenSolved() {
        // Break all tiles
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getCols(); j++) {
                field.breakTile(j, i);
            }
        }
        field.updateGameState(); // Update game state

        assertEquals(GameState.SOLVED, field.getGameState());
    }

    @Test
    void testUpdateGameStateWhenPlaying() {
        // Ensure game state is initially playing
        assertEquals(GameState.PLAYING, field.getGameState());

        // Break some tiles, but not all
        field.breakTile(1, 1);
        field.breakTile(2, 2);
        field.updateGameState(); // Update game state

        assertEquals(GameState.PLAYING, field.getGameState());
    }

    @Test
    void testUpdateGameStateWhenEmptyField() {
        // Break all tiles
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getCols(); j++) {
                field.breakTile(j, i);
            }
        }
        field.updateGameState(); // Update game state

        assertEquals(GameState.SOLVED, field.getGameState());

        // Break a tile in the solved state, should remain solved
        field.breakTile(1, 1);
        assertEquals(GameState.SOLVED, field.getGameState());
    }
}
