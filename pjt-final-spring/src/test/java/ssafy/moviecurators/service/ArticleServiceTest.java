package ssafy.moviecurators.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.domain.movies.Movie;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(value = false)
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    public void homeArticle() {
        List<Article> results = articleService.articleHome();
        for (Article result : results) {
            System.out.println("result.getTitle() = " + result.getTitle());
            System.out.println("result.getPoints() = " + result.getPoints());
        }
    }

}