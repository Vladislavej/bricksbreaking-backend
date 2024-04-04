package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

public class RatingServiceRestClient implements RatingService{

    @Autowired
    private RestTemplate restTemplate;
    private String url = "http://localhost:8080/api/rating";

    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForObject(url,rating,Void.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        ResponseEntity<Integer> rest = restTemplate.getForEntity(url + "/" + game, Integer.class);
        return rest.getBody();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        ResponseEntity<Integer> rest = restTemplate.getForEntity(url + "/" + game + "/" + player, Integer.class);
        return rest.getBody();
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}