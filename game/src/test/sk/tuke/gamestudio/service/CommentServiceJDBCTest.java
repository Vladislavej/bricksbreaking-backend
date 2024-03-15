package test.sk.tuke.gamestudio.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentServiceJDBC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

public class CommentServiceJDBCTest {
    private static final String TEST_GAME = "test_game";
    private CommentServiceJDBC commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceJDBC();
        commentService.reset(); // Reset the comments before each test
    }

    @Test
    void addCommentAndGetComments() {
        // Add some comments
        commentService.addComment(new Comment(TEST_GAME, "Player1", "Great game!", new java.util.Date()));
        commentService.addComment(new Comment(TEST_GAME, "Player2", "Awesome!", new java.util.Date()));
        commentService.addComment(new Comment(TEST_GAME, "Player3", "I love it!", new java.util.Date()));

        // Retrieve comments
        List<Comment> comments = commentService.getComments(TEST_GAME);

        // Check if the comments are retrieved correctly
        assertEquals(3, comments.size());
        assertEquals("I love it!", comments.get(2).getComment()); // Latest comment
        assertEquals("Awesome!", comments.get(1).getComment()); // Second latest comment
        assertEquals("Great game!", comments.get(0).getComment()); // Third latest comment
    }

    @Test
    void resetComments() {
        // Add some comments
        commentService.addComment(new Comment(TEST_GAME, "Player1", "Nice game!", new java.util.Date()));
        commentService.addComment(new Comment(TEST_GAME, "Player2", "Good job!", new java.util.Date()));

        // Reset comments
        commentService.reset();

        // Check if no comments exist after reset
        List<Comment> comments = commentService.getComments(TEST_GAME);
        assertEquals(0, comments.size());
    }
}
