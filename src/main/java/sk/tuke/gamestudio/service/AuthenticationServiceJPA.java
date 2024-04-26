package sk.tuke.gamestudio.service;

import jakarta.persistence.NoResultException;
import sk.tuke.gamestudio.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public class AuthenticationServiceJPA implements AuthenticationService{
    @PersistenceContext
    private EntityManager entityManager;

    public boolean register(User user){
        String select = "SELECT u FROM User u WHERE u.username = :username";
        try {
            entityManager.createQuery(select, User.class)
                    .setParameter("username", user.getUsername()).getSingleResult();
            return false;
        } catch (NoResultException ex) {
            entityManager.persist(user);
            return true;
        }
    }

    public boolean isRegistered(User user) {
        String select = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
        try{
            User matchingUser = entityManager.createQuery(select, User.class)
                    .setParameter("username", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .getSingleResult();
            return matchingUser != null;
        }catch (NoResultException e){
            return false;
        }
    }

}