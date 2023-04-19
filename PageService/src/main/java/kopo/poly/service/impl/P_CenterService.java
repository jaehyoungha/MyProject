package kopo.poly.service.impl;

import kopo.poly.dto.CenterDTO;
import kopo.poly.service.P_ICenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class P_CenterService implements P_ICenterService {

    private final RestTemplate restTemplate;


    @Override
    public List<CenterDTO> getCenter(CenterDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".getCenter API 호출 Start!!");


        // 내가 보낼 데이터를 생성
        HttpHeaders httpHeaders =new HttpHeaders(); // 헤더 정보를 담을 곳 <header> </header>
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        log.info("헤더 콘텐트 타입 : " + httpHeaders.getContentType());

        HttpEntity<CenterDTO> request = new HttpEntity<>(pDTO,httpHeaders); // entitiy 객체를 만들어줘 내가 보낼 데이터랑, header를 묶어서 하나의 객체가 되는거야

        log.info("요청 시작 !!");
        // restTemplate 결과를 response body 담아서 온다
        ResponseEntity<List> response = restTemplate.exchange("http://localhost:13000/center/CenterList",HttpMethod.POST, request, List.class);
        log.info("요청 끝 !!");

        log.info("제발 좀 : " + response.getBody());



        return (List<CenterDTO>) response.getBody();
    }

    @Override
    public int insertCenter(CenterDTO pDTO) throws Exception {
        return 0;
    }

    @Override
    public CenterDTO getCenterList(String userId) throws Exception {
        return null;
    }

    @Override
    public int deleteUserInfo(long centerSeq) throws Exception {
        return 0;
    }


    @Override
    public String getString() throws Exception {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:13000")
                .path("/center/getString")
                .encode()
                .build()
                .toUri();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        log.info("status code : " + responseEntity.getStatusCode());
        log.info("body : " + responseEntity.getBody());

        return responseEntity.getBody();
    }
}
