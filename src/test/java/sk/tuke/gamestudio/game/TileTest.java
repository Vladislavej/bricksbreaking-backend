package sk.tuke.gamestudio.game;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    @Test
    void getColor_ReturnsCorrectColor() {
        Tile tile = new Tile(TileInfo.RED);
        assertEquals(TileInfo.RED, tile.getColor());
    }

    @Test
    void breakNeighbours_BreaksCorrectNeighbours() {
        Field field = new Field(5, 5);
        Tile[][] tiles = field.getTiles();
        tiles[2][2] = new Tile(TileInfo.RED);
        tiles[1][2] = new Tile(TileInfo.RED);
        tiles[3][2] = new Tile(TileInfo.RED);
        tiles[2][1] = new Tile(TileInfo.GREEN);
        tiles[2][3] = new Tile(TileInfo.GREEN);

        tiles[2][2].breakNeighbours(2, 2, field);

        assertNull(tiles[2][2]); // Center tile should be broken
        assertNull(tiles[1][2]); // Upper tile should be broken
        assertNull(tiles[3][2]); // Lower tile should be broken
        assertNotNull(tiles[2][1]); // Left tile of different color should not be broken
        assertNotNull(tiles[2][3]); // Right tile of different color should not be broken
    }
}