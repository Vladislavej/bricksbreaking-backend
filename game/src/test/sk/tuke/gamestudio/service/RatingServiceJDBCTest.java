package test.sk.tuke.gamestudio.service;

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
        ratingService.reset(); // Reset the ratings before each test
    }

    @Test
    void setRatingAndGetRating() {
        // Set a rating
        Rating rating = new Rating(TEST_GAME, "Player1", 4, new java.util.Date());
        ratingService.setRating(rating);

        // Retrieve the rating
        int retrievedRating = ratingService.getRating(TEST_GAME, "Player1");

        // Check if the rating is retrieved correctly
        assertEquals(4, retrievedRating);
    }

    @Test
    void getAverageRating() {
        // Set some ratings
        ratingService.setRating(new Rating(TEST_GAME, "Player1", 3, new java.util.Date()));
        ratingService.setRating(new Rating(TEST_GAME, "Player2", 4, new java.util.Date()));
        ratingService.setRating(new Rating(TEST_GAME, "Player3", 5, new java.util.Date()));

        // Retrieve the average rating
        int averageRating = ratingService.getAverageRating(TEST_GAME);

        // Check if the average rating is calculated correctly
        assertEquals(4, averageRating);
    }

    @Test
    void resetRatings() {
        // Set some ratings
        ratingService.setRating(new Rating(TEST_GAME, "Player1", 3, new java.util.Date()));
        ratingService.setRating(new Rating(TEST_GAME, "Player2", 4, new java.util.Date()));

        // Reset ratings
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
