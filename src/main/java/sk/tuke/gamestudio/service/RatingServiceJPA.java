package sk.tuke.gamestudio.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Rating;

@Transactional
public class RatingServiceJPA implements RatingService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        if(getRating(rating.getGame(),rating.getPlayer())== 0) {
            entityManager.persist(rating);
        }
        else{
            Query updateQuery = entityManager.createNamedQuery("Rating.updateRating");
            updateQuery.setParameter("rating",rating.getRating());
            updateQuery.setParameter("game",rating.getGame());
            updateQuery.setParameter("player",rating.getPlayer());
            updateQuery.setParameter("rated_on",rating.getRatedOn());
            updateQuery.executeUpdate();
        }
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
