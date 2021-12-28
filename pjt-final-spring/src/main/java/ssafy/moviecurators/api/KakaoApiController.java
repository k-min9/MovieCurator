package ssafy.moviecurators.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.moviecurators.domain.movies.Movie;
import ssafy.moviecurators.domain.payments.KakaoPayApprovalVO;
import ssafy.moviecurators.dto.error.ErrorResponse;
import ssafy.moviecurators.dto.simple.SimpleMovieDto;
import ssafy.moviecurators.service.JwtTokenProvider;
import ssafy.moviecurators.service.KakaoPayService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class KakaoApiController {

    private final KakaoPayService kakaoPayService;

    private final JwtTokenProvider jwtTokenProvider;
    private final MessageSource messageSource;

    /**
     * 카카오페이 api에 필요한 다양한 값들을 조회[GET]
     * @return api사용에 필요한 값들을 http response body에 넣어서 보냄
     */
    @GetMapping("/kakaoPay/")
    public String kakaoPaymentReady() {

        String result = kakaoPayService.kakaoPayReady();

        return result;
    }

    /**
     * 카카오페이 결제 기능[POST]
     * @param pg_token 카카오로부터 콜백으로 받는 토큰
     * @param request
     * @return 결제가 성공했을 경우 임시로 결제된 금액과 관련내용을 http response body에 넣어서 보냄
     */
    @PostMapping("/kakaoPay/success/")
    public ResponseEntity kakaoPaySuccess(@RequestParam("pgToken") String pg_token,
                                          HttpServletRequest request) {

        String token = request.getHeader("Authorization").replaceFirst("JWT ", "");
        Long userId;
        if (token.equals("null")) {
            userId = -1L;
        } else if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(messageSource.getMessage("error.valid.jwt", null, LocaleContextHolder.getLocale())));
        } else {
            userId = jwtTokenProvider.getUserIdFromJwt(token);
        }

        try {
            KakaoPayApprovalVO kakaoPayApprovalVO = kakaoPayService.kakaoPaySuccess(pg_token, userId);
            return ResponseEntity.ok().body(kakaoPayApprovalVO);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
