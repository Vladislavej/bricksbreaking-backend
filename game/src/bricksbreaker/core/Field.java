package bricksbreaker.core;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Field  {
    private int rows;
    private int cols;
    private Tile[][] tiles;
    private GameState gameState;
    private int scoreThisMove;
    private int lives;
    private int brokenBricks;
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

    /**
     * Generates new bricks in the existing field.
     * @param numColors amount of different colors you want to generate.
     */
    public void generate(int numColors) {
        Random random = new Random();

        List<TileInfo> colors = Arrays.asList(TileInfo.values());
        TileInfo[] selectedColors = Arrays.copyOfRange(colors.toArray(new TileInfo[0]), 0, numColors);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TileInfo randomColor = selectedColors[random.nextInt(selectedColors.length)];
                while(randomColor == TileInfo.NONE) { randomColor = selectedColors[random.nextInt(selectedColors.length)]; }
                this.tiles[i][j] = new Tile(randomColor);
            }
        }
        System.out.println("Generated a new tile field with " + numColors + " different colors possible!");
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

        setScoreThisMove(getScoreThisMove() + tiles[y][x].getBrickScore());
        tiles[y][x].use(x, y, this);
        tiles[y][x] = null;
        brokenBricks += 1;
    }

    public void unite() {
        for (int i = 0; i < rows - 1; i++) { //gravity
            for (int j = 0; j < cols; j++) {
                if(tiles[i][j] != null && tiles[i+1][j] == null) {
                    tiles[i+1][j] = tiles[i][j];
                    tiles[i][j] = null;
                    unite();
                }
            }
        }
        for (int j = 0; j < cols; j++) {
            if (tiles[rows - 1][j] == null) {
                if (j < cols / 2) {
                    // gap in the left
                    for (int k = j + 1; k < cols; k++) {
                        for (int i = 0; i < rows; i++) {
                            if (tiles[i][k] != null) {
                                tiles[i][k - 1] = tiles[i][k];
                                tiles[i][k] = null;
                            }
                        }
                    }
                } else {
                    // gap in the right
                    for (int k = j - 1; k >= 0; k--) {
                        for (int i = 0; i < rows; i++) {
                            if (tiles[i][k] != null) {
                                tiles[i][k + 1] = tiles[i][k];
                                tiles[i][k] = null;
                            }
                        }
                    }
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
        if(lives <= 0) { gameState = GameState.FAILED; }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(tiles[i][j] != null) { return; }
            }
        }

        gameState = GameState.SOLVED;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getScoreThisMove() {
        return scoreThisMove;
    }

    public void setScoreThisMove(int scoreThisMove) {
        this.scoreThisMove = scoreThisMove;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getBrokenBricks() {
        return brokenBricks;
    }

    public void setBrokenBricks(int brokenBricks) {
        this.brokenBricks = brokenBricks;
    }
}