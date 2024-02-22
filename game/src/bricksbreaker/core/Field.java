package bricksbreaker.core;

import java.util.Random;

public class Field  {
    private int rows;
    private int cols;
    private Tile[][] tiles;
    private GameState gameState;
    private int lives;
    public Field(int rows, int cols, int lives) {
        this.rows = rows;
        this.cols = cols;
        tiles = new Tile[rows][cols];
        this.gameState = GameState.PLAYING;
        this.lives = lives;
    }
    public Tile[][] getTiles() {
        return tiles;
    }

    public void generate() {
        Random random = new Random();
        TileColor[] colors = TileColor.values();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TileColor randomColor = colors[random.nextInt(colors.length)];
                this.tiles[i][j] = new Tile(randomColor);
            }
        }
        System.out.println("Generated a new tile field");
    }

    public void breakTile(int x, int y) {
        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            System.out.println("Invalid tile coordinates: (" + x + ", " + y + ")");
            return;
        }
        if (tiles[y][x] == null) {
            System.out.println("No tile found at coordinates: (" + x + ", " + y + ")");
            return;
        }

        TileColor tileColor = tiles[y][x].getColor();
        tiles[y][x].breakNeighbours(x, y, this);
        tiles[y][x] = null;
        System.out.println("Destroyed brick at (" + x + ", " + y + ") of color: " + tileColor);
        checkGameState();
    }

    public void unite() {
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols; j++) {
                if(tiles[i][j] != null && tiles[i+1][j] == null) {
                    tiles[i+1][j] = tiles[i][j];
                    tiles[i][j] = null;
                    unite();
                }
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void checkGameState() {
        if(lives <= 0) {
            gameState = GameState.FAILED;
            return;
        }
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols; j++) {
                if(tiles[i][j] != null) { return; }
            }
        }
        gameState = GameState.SOLVED;
    }
}