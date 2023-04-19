package kopo.poly.service.impl;

import kopo.poly.dto.UserDTO;
import kopo.poly.service.P_IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
@RequiredArgsConstructor
public class P_UserService implements P_IUserService {

    private final RestTemplate restTemplate;

    @Override
    public int insertUserInfo(UserDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + "insertUserInfo(page_Service 회원가입) 시작");


        // 내가 보낼 데이터를 생성
        HttpHeaders httpHeaders = new HttpHeaders(); // 헤더 정보를 담을 곳 <header> </header>
        HttpEntity<UserDTO> request = new HttpEntity<>(pDTO, null); // entitiy 객체를 만들어줘 내가 보낼 데이터랑, header를 묶어서 하나의 객체가 되는거야

        // restTemplate 결과를 response body 담아서 온다
        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:12000/user/insertUserInfo", HttpMethod.POST, request, Integer.class); //반환타입

        // 200인지 400인지 500인지 상태 코드 정상확인
        response.getStatusCode();

        //실제 데이터를 넣어놓은곳
        response.getBody();

        // 헤더정보
        response.getHeaders();

        return response.getBody();
    }

    @Override
    public int checkUserId(String userId) throws Exception {
        log.info(this.getClass().getName() + "checkUserId(page_Service 아이디 중복체크) start");

        log.info("아이디 중복 체크 서비스 : " + userId);

        //  내가 보낼 데이터를 생성
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(userId, headers);


        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:12000/user/checkUserId", HttpMethod.POST, entity, Integer.class);
        // restTemplate 결과를 response body 담아서 온다
        log.info("아이디 중복체크 결과 " + response.getBody());


        return response.getBody();
    }

    @Override
    public int checkUserEmail(String userEmail) throws Exception {
        log.info(this.getClass().getName() + "checkUserEmail(page_Service 이메일 중복체크) start");
        log.info("이메일 중복 체크 서비스 : " + userEmail);


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(userEmail, headers);

        ResponseEntity<Integer> response = restTemplate.exchange("http://localhost:12000/user/checkUserEmail", HttpMethod.POST, entity, Integer.class);

        log.info("이메일 중복체크 결과 " + response.getBody());

        return response.getBody();
    }

    @Override
    public String findUserId(UserDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + "findUserId(page_Service 아이디 찾기) START");

        HttpHeaders headers= new HttpHeaders();
        HttpEntity<UserDTO> request = new HttpEntity<>(pDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:12000/user/findUserId", HttpMethod.POST, request, String.class);

        log.info(this.getClass().getName() + "findUserId(page_Service 아이디 찾기) END");

        return response.getBody();
    }
}
