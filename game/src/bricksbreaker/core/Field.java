package bricksbreaker.core;

import java.util.Random;

public class Field  {
    private int rows;
    private int cols;
    private Brick[][] bricks;
    private GameState gameState;
    public Field(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        bricks = new Brick[rows][cols];
        this.gameState = GameState.PLAYING;
    }
    public Brick[][] getBricks() {
        return bricks;
    }

    public void generate() {
        Random random = new Random();
        BrickColor[] colors = BrickColor.values();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                BrickColor randomColor = colors[random.nextInt(colors.length)];
                this.bricks[i][j] = new Brick(randomColor);
            }
        }
    }

    public boolean breakBrick(int x, int y) {
        if(bricks[x][y] == null) { return false; }
        BrickColor brickColor = bricks[x][y].getColor();
        bricks[x][y] = null;
        checkNeighbours(x,y, brickColor);
        System.out.println("Destroyed brick at " + x + " " + y + " of color = " + brickColor);
        return true;
    }

    private void checkNeighbours(int x , int y, BrickColor brickColor) {
        //left
        if (y - 1 >= 0 && bricks[x][y - 1] != null && bricks[x][y - 1].getColor() == brickColor) { breakBrick(x, y - 1); }
        //top
        if (x - 1 >= 0 && bricks[x - 1][y] != null && bricks[x - 1][y].getColor() == brickColor) { breakBrick(x - 1, y); }
        //right
        if (x + 1 < rows && bricks[x + 1][y] != null && bricks[x + 1][y].getColor() == brickColor) { breakBrick(x + 1, y); }
        //bottom
        if (y + 1 < cols && bricks[x][y + 1] != null && bricks[x][y + 1].getColor() == brickColor) { breakBrick(x, y + 1); }
    }

    public void unite() {
        for (int i = 0; i < rows - 1; i++) {
            for (int j = 0; j < cols; j++) {
                if(bricks[i][j] != null && bricks[i+1][j] == null) {
                    bricks[i+1][j] = bricks[i][j];
                    bricks[i][j] = null;
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
