package ssafy.moviecurators.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.repository.MovieRepository;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class MovieService {

    private final MovieRepository movieRepository;

    //home 용 영화 조회 12편, 선정 알고리즘은 동봉된 01.ML_recommend.py와 기술서 참조
    public List<Movie> homeMovie() {
        List<Long> homeMovies = Arrays.asList(588228L, 508943L, 438631L, 566525L, 436969L, 550988L, 522402L, 497698L, 451048L, 459151L, 370172L, 482373L);
        return movieRepository.findByIds(homeMovies);
    }


}
