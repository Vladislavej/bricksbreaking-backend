package bricksbreaker.core;

import bricksbreaker.consoleui.ConsoleUI;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;

public class GameManager {
    private Field field;
    private int score;
    private int lives;
    private int numColors;
    private ScoreServiceJDBC scoreServiceJDBC;
    private ConsoleUI consoleUI;

    public GameManager() {
        this.scoreServiceJDBC = new ScoreServiceJDBC();
        this.consoleUI = new ConsoleUI(null);

        play();
    }
    private Field initialiazeField() {
        int[] fieldSpec = consoleUI.getFieldSpecs();
        this.numColors = fieldSpec[2];
        return new Field(fieldSpec[0],fieldSpec[1]);
    }
    private void calculateStats() {
        score += field.getScoreThisMove() * field.getBrokenBricks();

        if(field.getBrokenBricks() == 1) { lives -= 1; }
        if(lives < 1) { field.setGameState(GameState.FAILED); }

        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
    }
    private void prepareGame() {
        field = null;
        while (field == null) { field = initialiazeField(); }
        consoleUI.setField(field);
        this.field.generate(numColors);
        lives = 3;
        field.setBrokenBricks(0);
        field.setScoreThisMove(0);
        score = 0;
        field.setGameState(GameState.PLAYING);
    }
    public void play() {
        prepareGame();
        do {
            consoleUI.showStats(score, lives);
            consoleUI.showField();
            int[] coordinates = consoleUI.handleInput();
            field.breakTile(coordinates[0], coordinates[1]);
            calculateStats();
            field.unite();
            field.updateGameState();
        } while(field.getGameState() == GameState.PLAYING);

        if(field.getGameState() == GameState.SOLVED) {
            System.out.println("Solved!");
        } else if(field.getGameState() == GameState.FAILED){
            System.out.println("Failed!");
        }

        consoleUI.showStats(score, lives);
        consoleUI.showField();

        Score finalScore = new Score("Bricks Breaking", "vladej", score, new Date());
        scoreServiceJDBC.addScore(finalScore);
        List<Score> topScores = scoreServiceJDBC.getTopScores("Bricks Breaking");

        consoleUI.showHighScores(topScores);

        if(consoleUI.playAgain()) { play(); }
    }
}
