package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

@Service
public class RatingServiceRestClient implements RatingService{

    @Autowired
    private RestTemplate restTemplate;
    private String url = "http://localhost:8080/api/rating";

    @Override
    public void setRating(Rating rating) throws RatingException {
        try {
            restTemplate.postForObject(url, rating, Void.class);
        } catch (Exception e) {
            throw new CommentException("setRating", e);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        try {
            ResponseEntity<Integer> rest = restTemplate.getForEntity(url + "/" + game, Integer.class);
            return rest.getBody();
        } catch (Exception e) {
            throw new CommentException("getAverageRating", e);
        }
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            ResponseEntity<Integer> rest = restTemplate.getForEntity(url + "/" + game + "/" + player, Integer.class);
            return rest.getBody();
        } catch (Exception e) {
            throw new CommentException("getRating", e);
        }
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}