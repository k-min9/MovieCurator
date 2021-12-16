package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.domain.payments.KakaoPayApprovalVO;
import ssafy.moviecurators.dto.simple.SimpleMovieDto;
import ssafy.moviecurators.service.KakaoPayService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class KakaoApiController {

    private final KakaoPayService kakaoPayService;

    @GetMapping("/kakaoPay/")
    public String kakaoPaymentReady() {

        String result = kakaoPayService.kakaoPayReady();

        return result;
    }

    @PostMapping("/kakaoPay/success/")
    public ResponseEntity kakaoPaySuccess(@RequestParam("pgToken") String pg_token) {

        try {
            KakaoPayApprovalVO kakaoPayApprovalVO = kakaoPayService.kakaoPaySuccess(pg_token);
            return ResponseEntity.ok().body(kakaoPayApprovalVO);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
