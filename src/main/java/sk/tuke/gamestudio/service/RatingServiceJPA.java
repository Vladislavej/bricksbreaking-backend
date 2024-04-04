package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
            entityManager.createNamedQuery("Rating.UpdateRating")
                    .setParameter("rating", rating.getRating())
                    .setParameter("ratedOn", rating.getRatedOn())
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .executeUpdate();
            entityManager.persist(rating);
    }


    @Override
    public int getAverageRating(String game) throws RatingException {
        Object result = entityManager.createNamedQuery("Rating.getAverageRating").setParameter("game", game).getSingleResult();
        if (result != null) {
            double averageRating = ((Number) result).doubleValue();
            return (int) Math.round(averageRating);
        } else {
            return 0;
        }
    }


    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            Object result = entityManager.createNamedQuery("Rating.getRating").setParameter("game", game).setParameter("player", player).getSingleResult();
            if (result != null) {
                double rating = ((Number) result).doubleValue();
                return (int) Math.round(rating);
            } else {
                return 0;
            }
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public void reset() {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
        // alebo:
        // entityManager.createNativeQuery("delete from score").executeUpdate();
    }
}
