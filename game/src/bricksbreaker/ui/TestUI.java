package bricksbreaker.ui;

import bricksbreaker.core.Field;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

public class TestUI implements GameUI{
    private int i;
    private Field field;
    private String player;

    public TestUI(Field field) {
        this.field = field;
        this.player = "test";
        this.i = 1;
    }

    @Override
    public int[] getFieldSpecs() {
        return new int[0];
    }

    @Override
    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public void showStats(int score, int lives) {

    }

    @Override
    public void showField() {

    }

    @Override
    public int[] handleMove() {
        return new int[]{i};
    }

    @Override
    public void showHighScores(List<Score> topScores) {

    }

    @Override
    public boolean playAgain() {
        return false;
    }

    @Override
    public int showMainMenu() {
        return i;
    }

    @Override
    public String changePlayerName() {
        return "test";
    }

    @Override
    public String addComment(List<Comment> commentList) {
        return "test";
    }

    @Override
    public int addRating() {
        return 0;
    }

    @Override
    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public void passAverageRating(int rating) {

    }

    @Override
    public void passLastRating(int rating) {

    }

    @Override
    public void showWin() {

    }

    @Override
    public void showFail() {

    }

    @Override
    public void showHelp() {

    }

    public void passInt(int i) {
        this.i = i;
    }
}
