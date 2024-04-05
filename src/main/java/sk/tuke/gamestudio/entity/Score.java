package sk.tuke.gamestudio.entity;

import jakarta.persistence.*;
import java.util.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Score {
    @Id
    @GeneratedValue
    private int ident; //identifikator
    private String game;
    private String player;
    private int points;
    private Date playedOn;

    public Score(String game, String player, int points, Date playedOn) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.playedOn = playedOn;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPlayedOn(Date playedOn) {
        this.playedOn = playedOn;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }
}
