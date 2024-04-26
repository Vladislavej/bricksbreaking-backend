package sk.tuke.gamestudio.service;

import jakarta.persistence.NoResultException;
import sk.tuke.gamestudio.entity.User;

public interface AuthenticationService {
    public boolean register(User user) throws AuthenticationException;
    public boolean isRegistered(User user) throws AuthenticationException;


}
