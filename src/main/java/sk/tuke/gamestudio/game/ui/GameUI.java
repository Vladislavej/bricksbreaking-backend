package sk.tuke.gamestudio.game.ui;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.Field;

import java.util.List;

public interface GameUI {
    int[] getFieldSpecs();
    void setField(Field field);
    void showStats(int score, int lives);
    void showField();
    int[] handleMove();
    void showHighScores(List<Score> topScores);
    boolean playAgain();
    int showMainMenu();
    String changePlayerName();
    String addComment(List<Comment> commentList);
    int addRating();
    void setPlayer(String player);
    void passAverageRating(int rating);
    void passLastRating(int rating);
    void showWin();
    void showFail();
    void showHelp();
}