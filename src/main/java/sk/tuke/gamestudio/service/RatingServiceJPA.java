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
        if(rating.getRating() > 5 || rating.getRating() < 1) { throw new RatingException("Rating out of bounds"); }
        if(getRating(rating.getGame(), rating.getPlayer()) == 0) {
            entityManager.persist(rating);
        } else {
            Query updateQuery = entityManager.createQuery("UPDATE Rating r SET r.rating = :rating, r.ratedOn = :rated_on WHERE r.game = :game AND r.player = :player");
            updateQuery.setParameter("rating",rating.getRating());
            updateQuery.setParameter("game",rating.getGame());
            updateQuery.setParameter("player",rating.getPlayer());
            updateQuery.setParameter("rated_on",rating.getRatedOn());
            updateQuery.executeUpdate();
        }
    }


    @Override
    public int getAverageRating(String game) throws RatingException {
        try{
            Query query =  entityManager.createQuery("SELECT avg(r.rating) FROM Rating r WHERE r.game = :game")
                    .setParameter("game",game);
            return ((Number) query.getSingleResult()).intValue();
        }catch(Exception e){
            return 0;
        }
    }


    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            Query query = entityManager.createQuery("SELECT r.rating FROM Rating r WHERE r.game = :game AND r.player = :player")
                    .setParameter("game", game).setParameter("player", player);
            return ((Number) query.getSingleResult()).intValue();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE FROM rating").executeUpdate();
    }
}
