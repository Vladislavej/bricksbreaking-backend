package sk.tuke.gamestudio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingException;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RatingServiceJDBCTest {
    private static final String TEST_GAME = "test_game";
    private RatingServiceJDBC ratingService;

    @BeforeEach
    void setUp() {
        ratingService = new RatingServiceJDBC();
        ratingService.reset();
    }

    @Test
    void setRatingAndGetRating() {
        Rating rating = new Rating(TEST_GAME, "Player1", 4, new java.util.Date());
        ratingService.setRating(rating);

        int retrievedRating = ratingService.getRating(TEST_GAME, "Player1");

        assertEquals(4, retrievedRating);
    }

    @Test
    void getAverageRating() {
        ratingService.setRating(new Rating(TEST_GAME, "Player1", 3, new java.util.Date()));
        ratingService.setRating(new Rating(TEST_GAME, "Player2", 4, new java.util.Date()));
        ratingService.setRating(new Rating(TEST_GAME, "Player3", 5, new java.util.Date()));

        int averageRating = ratingService.getAverageRating(TEST_GAME);

        assertEquals(4, averageRating);
    }

    @Test
    void resetRatings() {
        ratingService.setRating(new Rating(TEST_GAME, "Player1", 3, new java.util.Date()));
        ratingService.setRating(new Rating(TEST_GAME, "Player2", 4, new java.util.Date()));

        ratingService.reset();

        // Check if no ratings exist after reset
        assertEquals(-1, ratingService.getRating(TEST_GAME, "Player1"));
        assertEquals(-1, ratingService.getRating(TEST_GAME, "Player2"));
        assertEquals(0, ratingService.getAverageRating(TEST_GAME));
    }

    @Test
    void setInvalidRating() {
        // Attempt to set an invalid rating
        assertThrows(RatingException.class, () -> {
            Rating invalidRating = new Rating(TEST_GAME, "Player1", 6, new java.util.Date());
            ratingService.setRating(invalidRating);
        });
    }
}
