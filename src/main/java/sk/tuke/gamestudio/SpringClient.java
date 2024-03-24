package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.Field;
import sk.tuke.gamestudio.game.GameManager;
import sk.tuke.gamestudio.game.ui.console.ConsoleUI;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
public class SpringClient {

    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args);
    }

    @Bean
    public CommandLineRunner runner(GameManager gameManager, ConsoleUI consoleUI) {
        return args -> gameManager.setGameUI(consoleUI);
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI(null);
    }

    @Bean
    public GameManager gameManager(ConsoleUI consoleUI) {
        return new GameManager(consoleUI);
    }

    @Bean
    public ScoreService scoreService() {
        return new ScoreServiceJPA();
    }
    @Bean
    public RatingService ratingService() {
        return new RatingServiceJPA();
    }
    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
    }
}
