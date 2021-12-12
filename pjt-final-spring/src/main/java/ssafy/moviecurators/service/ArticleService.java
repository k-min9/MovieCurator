package ssafy.moviecurators.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Article;
import ssafy.moviecurators.repository.ArticleRepository;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> articleHome() {
        // 1week
        OffsetDateTime end = OffsetDateTime.now();
        OffsetDateTime start = end.minusDays(7);

        return articleRepository.findTop6ByCreatedBetweenOrderByPointsDesc(start, end);
    }

    public List<Article> articleList(Long id) {
        return articleRepository.articleList(id);
    }
}
