package sk.tuke.gamestudio.game;

import lombok.Getter;

import java.util.Objects;

public class WebGameManager {
    @Getter
    private Field field;
    @Getter
    private int score;
    @Getter
    private int lives;
    private int numColors;

    public WebGameManager() {
    }
    public void prepareGame(int gameMode, int rowsPar, int colsPar, int numColorsPar) {
        int rows = 5;
        int cols = 5;
        int numColors = 2;

        switch (gameMode) {
            case 0:
                rows = 10;
                cols = 10;
                numColors = 3;
                break;
            case 1:
                rows = 15;
                cols = 15;
                numColors = 4;
                break;
            case 2:
                rows = 20;
                cols = 20;
                numColors = 6;
                break;
            case 3:
                rows = rowsPar;
                cols = colsPar;
                numColors = numColorsPar;
        }

        field = null;
        while (field == null) {
            field = new Field(rows,cols);
        }
        this.field.generate(numColors);
        lives = 3;
        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
        score = 0;
        field.setGameState(GameState.PLAYING);
    }
    public void play(int gameMode) {
        prepareGame(gameMode, 0 ,0 ,0);
    }

    public void calculateStats() {
        score += field.getScoreThisMove() * field.getBrokenBricks();

        if(field.getBrokenBricks() == 1) { lives -= 1; }
        if(lives < 1) { field.setGameState(GameState.FAILED); }

        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
    }

    public void gameLoop(int x, int y) {
        field.breakTile(x, y);

        calculateStats();

        for (int i = 0; i < field.getCols(); i++) {
            field.unite();
        }

        field.updateGameState();
    }
}