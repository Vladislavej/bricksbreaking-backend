package sk.tuke.gamestudio.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    private Field field;

    @BeforeEach
    void setUp() {
        field = new Field(5, 5);
        field.generate(3);
    }

    @Test
    void testBreakTileInvalidCoordinates() {
        int initialBrokenBricks = field.getBrokenBricks();
        field.breakTile(-1, 2);
        assertEquals(initialBrokenBricks, field.getBrokenBricks());
    }

    @Test
    void testUnite() {
        for (int j = 0; j < field.getCols(); j++) {
            field.getTiles()[field.getRows() - 1][j] = new Tile(TileInfo.BLUE);
        }

        field.breakTile(2, field.getRows() - 2);
        field.breakTile(3, field.getRows() - 2);

        field.unite();

        assertNotNull(field.getTiles()[field.getRows() - 1][2]);
        assertNotNull(field.getTiles()[field.getRows() - 1][3]);
    }

    @Test
    void testUpdateGameStateWhenSolved() {
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getCols(); j++) {
                field.breakTile(j, i);
            }
        }
        field.updateGameState();

        assertEquals(GameState.SOLVED, field.getGameState());
    }

    @Test
    void testUpdateGameStateWhenPlaying() {
        assertEquals(GameState.PLAYING, field.getGameState());

        field.breakTile(1, 1);
        field.breakTile(2, 2);
        field.updateGameState();

        assertEquals(GameState.PLAYING, field.getGameState());
    }

    @Test
    void testUpdateGameStateWhenEmptyField() {
        for (int i = 0; i < field.getRows(); i++) {
            for (int j = 0; j < field.getCols(); j++) {
                field.breakTile(j, i);
            }
        }
        field.updateGameState();

        assertEquals(GameState.SOLVED, field.getGameState());

        field.breakTile(1, 1);
        assertEquals(GameState.SOLVED, field.getGameState());
    }
}
