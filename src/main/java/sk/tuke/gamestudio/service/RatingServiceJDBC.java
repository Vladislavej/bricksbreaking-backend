package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingServiceJDBC implements  RatingService {
    public static final String URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String USER = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String SELECT = "SELECT game, player, rating, ratedOn FROM rating WHERE game = ? ORDER BY ratedOn DESC LIMIT 10";
    public static final String DELETE = "DELETE FROM rating";
    public static final String INSERT = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";

    @Override
    public void setRating(Rating rating) throws RatingException {
        if (rating.getRating() < 0 || rating.getRating() > 5) {
            throw new RatingException("Rating value out of range");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String selectQuery = "SELECT COUNT(*) AS count FROM rating WHERE game = ? AND player = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, rating.getGame());
                selectStatement.setString(2, rating.getPlayer());

                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        if (count > 0) {
                            // If a rating already exists, update it
                            String updateQuery = "UPDATE rating SET rating = ?, ratedOn = ? WHERE game = ? AND player = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setInt(1, rating.getRating());
                                updateStatement.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
                                updateStatement.setString(3, rating.getGame());
                                updateStatement.setString(4, rating.getPlayer());
                                updateStatement.executeUpdate();
                            }
                        } else {
                            // If a rating doesn't exist, insert a new one
                            String insertQuery = "INSERT INTO rating (game, player, rating, ratedOn) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                                insertStatement.setString(1, rating.getGame());
                                insertStatement.setString(2, rating.getPlayer());
                                insertStatement.setInt(3, rating.getRating());
                                insertStatement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                                insertStatement.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem setting rating", e);
        }
    }


    @Override
    public int getAverageRating(String game) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT AVG(rating) AS avg_rating FROM rating WHERE game = ?");
        ) {
            statement.setString(1, game);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("avg_rating");
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting average rating", e);
        }
        return 0;
    }


    @Override
    public int getRating(String game, String player) throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT rating FROM rating WHERE game = ? AND player = ?");
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("rating");
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem getting rating", e);
        }
        return -1;
    }


    @Override
    public void reset() throws RatingException {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting rating", e);
        }
    }
}
