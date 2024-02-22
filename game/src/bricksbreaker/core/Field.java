package bricksbreaker.core;

import java.util.Random;

public class Field  {
    private int rows;
    private int cols;
    private Tile[][] tiles;
    private GameState gameState;
    public Field(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        tiles = new Tile[rows][cols];
        this.gameState = GameState.PLAYING;
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

    public boolean breakTile(int x, int y) {
        if(tiles[x][y] == null) { return false; }
        TileColor tileColor = tiles[x][y].getColor();
        tiles[x][y] = null;
        checkNeighbours(x,y, tileColor);
        System.out.println("Destroyed brick at " + x + " " + y + " of color = " + tileColor);
        return true;
    }

    private void checkNeighbours(int x , int y, TileColor tileColor) {
        //left
        if (y - 1 >= 0 && tiles[x][y - 1] != null && tiles[x][y - 1].getColor() == tileColor) { breakTile(x, y - 1); }
        //top
        if (x - 1 >= 0 && tiles[x - 1][y] != null && tiles[x - 1][y].getColor() == tileColor) { breakTile(x - 1, y); }
        //right
        if (x + 1 < rows && tiles[x + 1][y] != null && tiles[x + 1][y].getColor() == tileColor) { breakTile(x + 1, y); }
        //bottom
        if (y + 1 < cols && tiles[x][y + 1] != null && tiles[x][y + 1].getColor() == tileColor) { breakTile(x, y + 1); }
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

}
