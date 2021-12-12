package ssafy.moviecurators.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.repository.ArticleRepository;
import ssafy.moviecurators.repository.MovieRepository;
import ssafy.moviecurators.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public List<Article> articleHome() {
        // 1week
        OffsetDateTime end = OffsetDateTime.now();
        OffsetDateTime start = end.minusDays(7);

        return articleRepository.findTop6ByCreatedBetweenOrderByPointsDesc(start, end);
    }

    public List<Article> articleList(Long id) {
        return articleRepository.articleList(id);
    }

    public Article articleDetail(Long movieId, Long userId) {
        return articleRepository.articleDetail(movieId, userId);
    }

    @Transactional
    public Article articleDetailPost(Article article, Long movieId, Long userId) {

        article.setMovie(movieRepository.getById(movieId));
        article.setUser(userRepository.getById(userId));
        articleRepository.save(article);
        return article;
    }

    @Transactional
    public Article articleDetailPut(Article articleChange, Long movieId, Long userId) {

        Article article = articleRepository.articleDetail(movieId, userId);
        article.setTitle(articleChange.getTitle());
        article.setContent(articleChange.getContent());
        article.setRate(articleChange.getRate());
        return article;

    }



}
