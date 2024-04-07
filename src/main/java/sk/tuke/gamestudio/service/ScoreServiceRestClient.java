package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;

import java.util.Arrays;
import java.util.List;

@Service
public class ScoreServiceRestClient implements ScoreService {
    private final String url = "http://localhost:8080/api/score";

    @Autowired
    private RestTemplate restTemplate;
    //private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void addScore(Score score) {
        try {
            restTemplate.postForEntity(url, score, Score.class);
        } catch (Exception e) {
            throw new CommentException("addScore", e);
        }
    }

    @Override
    public List<Score> getTopScores(String gameName) {
        try {
            return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, Score[].class).getBody());
        } catch (Exception e) {
            throw new CommentException("getTopScores", e);
        }
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}