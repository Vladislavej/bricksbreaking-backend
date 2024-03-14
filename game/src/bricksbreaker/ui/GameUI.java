package bricksbreaker.ui;

import bricksbreaker.core.Field;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public interface GameUI {
    int[] getFieldSpecs();
    void setField(Field field);
    void showStats(int score, int lives);
    void showField();
    int[] handleMove();
    void showHighScores(List<Score> topScores);
    boolean playAgain();
    int mainMenu();
    String playerName();
    String getComment();
    int getRating();
    void setPlayer(String player);
    void passAverageRating(int rating);
    void showWin();
    void showFail();
    void showHelp();
}