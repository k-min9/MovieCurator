package ssafy.moviecurators.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.repository.MovieRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(readOnly = true)  // 기본적으로 트랜잭션 안에서만 데이터 변경하게 설정(그만큼 최적화 되어 읽는게 빨라짐)
@RequiredArgsConstructor  // 생성자 주입 처리
public class MovieService {

    private final MovieRepository movieRepository;

    //home 용 영화 조회 12편, 선정 알고리즘은 동봉된 01.ML_recommend.py와 기술서 참조
    public List<Movie> homeMovie() {
        List<Long> homeMovies = Arrays.asList(588228L, 508943L, 438631L, 436969L, 566525L, 550988L, 522402L, 497698L, 451048L, 370172L, 459151L, 482373L);
        return movieRepository.findByIds(homeMovies);
    }

    public List<Movie> movieListMain(String filter) {
        if (filter.equals("popularity")){return movieRepository.findTop30ByOrderByPopularityDesc();}
        else if(filter.equals("release_date")){return movieRepository.findTop30ByOrderByReleaseDateDesc();}
        else {return movieRepository.findTop30ByOrderByVoteAverageDesc();}
    }

    public List<Movie> movieListGenre(String name) {
        return movieRepository.movieListGenre(name, PageRequest.of(0,30));
    }

    public List<Movie> movieSearch(String searchKeyword) {
        return movieRepository.findTop25ByTitleContainingOrOriginalTitleContainingIgnoreCase(searchKeyword, searchKeyword);
    }

    public List<Movie> moviesRecommend(Long id) {
        Movie movie = movieRepository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화의 정보가 없습니다."));

        List<Long> recommendIds = movie.getMovie_reference_overview()  // 무슨 짓을 해도 여기까지 List<Integer>
                .stream().mapToLong(Integer::longValue).boxed().collect(toList());

        return movieRepository.findByIds(recommendIds);
    }
}
