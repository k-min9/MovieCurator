package ssafy.moviecurators.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ssafy.moviecurators.domain.payments.KakaoPayApprovalVO;
import ssafy.moviecurators.domain.payments.KakaoPayReadyVO;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class KakaoPayService {

    private static final String HOST = "https://kapi.kakao.com";
    // 카카오페이 내 어플리케이션 플랫폼에서 주소 관리
    private static final String FRONTSERVER = "https://moviecurators-spring.netlify.app";
//    private static final String FRONTSERVER = "http://localhost:8081";

    @Value("${key.kakao.admin}")
    String ADMIN_KEY;

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;

    public String kakaoPayReady() {

        System.out.println("KakaoAK " + ADMIN_KEY);
        log.info("KakaoAK " + ADMIN_KEY);

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);  // admin 키
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);  // ? 님 뭐임??
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "MovieCurators");
        params.add("item_name", "Premium");
        params.add("quantity", "1");
        params.add("total_amount", "1100");
        params.add("tax_free_amount", "100");

        // 백엔드 쪽 리턴 주소 설정
        params.add("approval_url", FRONTSERVER + "/kakaoPay/success");  // 모든 절차가 끝나면 저기로 보냄. 체크
        params.add("cancel_url", FRONTSERVER + "/kakaoPayCancel");  // 안쓰임; 시험환경에서 확인 방도가 없음
        params.add("fail_url", FRONTSERVER + "/kakaoPaySuccessFail");  // 안쓰임; 시험환경에서 확인 방도가 없음

        // 헤더 + 바디
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        // 받아서 넣기
        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

//            log.info("받은 정보 : " + kakaoPayReadyVO);

            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "에러 발생";
    }

    public KakaoPayApprovalVO kakaoPaySuccess(String pg_token) {

        RestTemplate restTemplate = new RestTemplate();

//        log.info("기존 정보 : " + kakaoPayReadyVO);

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + ADMIN_KEY);  // admin 키
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", "1001");
        params.add("partner_user_id", "MovieCurators");
        params.add("pg_token", pg_token);
        params.add("total_amount", "1100");

        // 헤더 + 바디
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
//            log.info("카카오 성공" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
