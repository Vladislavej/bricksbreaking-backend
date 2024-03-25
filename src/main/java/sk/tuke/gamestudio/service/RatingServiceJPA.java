package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        if (rating.getRating() < 0 || rating.getRating() > 5) {
            throw new RatingException("Rating value out of range");
        }

        try {
            entityManager.createNamedQuery("Rating.setOrUpdateRating")
                    .setParameter("rating", rating.getRating())
                    .setParameter("ratedOn", rating.getRatedOn())
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .executeUpdate();
        } catch (Exception e) {
            throw new RatingException("Problem setting or updating rating", e);
        }
    }


    @Override
    public int getAverageRating(String game) throws RatingException {
        Object result = entityManager.createNamedQuery("Rating.getAverageRating").setParameter("game", game).getSingleResult();
        if (result != null) {
            double averageRating = ((Number) result).doubleValue();
            return (int) Math.round(averageRating);
        } else {
            throw new RatingException("No rating found for the specified game: " + game);
        }
    }


    @Override
    public int getRating(String game, String player) throws RatingException {
        Object result = entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("player", player).getSingleResult();
        if (result != null) {
            double rating = ((Number) result).doubleValue();
            return (int) Math.round(rating);
        } else {
            throw new RatingException("No rating found for the specified game: " + game);
        }
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
        // alebo:
        // entityManager.createNativeQuery("delete from score").executeUpdate();
    }
}
